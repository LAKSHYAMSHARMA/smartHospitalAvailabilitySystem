package smartHospitalAvailabilitySystem.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import smartHospitalAvailabilitySystem.dto.AmbulanceDTO;
import smartHospitalAvailabilitySystem.dto.BookingRequestDTO;
import smartHospitalAvailabilitySystem.dto.BookingResponseDTO;
import smartHospitalAvailabilitySystem.dto.DoctorDTO;
import smartHospitalAvailabilitySystem.dto.HospitalDTO;
import smartHospitalAvailabilitySystem.dto.UserDTO;
import smartHospitalAvailabilitySystem.entity.Ambulance;
import smartHospitalAvailabilitySystem.entity.AppUser;
import smartHospitalAvailabilitySystem.entity.Booking;
import smartHospitalAvailabilitySystem.entity.Doctor;
import smartHospitalAvailabilitySystem.entity.Hospital;
import smartHospitalAvailabilitySystem.repository.AmbulanceRepository;
import smartHospitalAvailabilitySystem.repository.BookingRepository;
import smartHospitalAvailabilitySystem.repository.DoctorRepository;
import smartHospitalAvailabilitySystem.repository.HospitalRepository;
import smartHospitalAvailabilitySystem.repository.UserRepository;
import smartHospitalAvailabilitySystem.service.BookingService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServicesImplementation implements BookingService {

    private static final String CONFIRMED = "CONFIRMED";
    private static final String CANCELLED = "CANCELLED";

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final AmbulanceRepository ambulanceRepository;
    private final ModelMapper modelMapper;

    public BookingServicesImplementation(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            HospitalRepository hospitalRepository,
            DoctorRepository doctorRepository,
            AmbulanceRepository ambulanceRepository,
            ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.hospitalRepository = hospitalRepository;
        this.doctorRepository = doctorRepository;
        this.ambulanceRepository = ambulanceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"ambulances", "hospital", "hospitals"}, allEntries = true)
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {
        AppUser user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + requestDTO.getUserId()));
        Hospital hospital = hospitalRepository.findById(requestDTO.getHospitalId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Hospital not found with id: " + requestDTO.getHospitalId()));

        Doctor doctor = findDoctor(requestDTO.getDoctorId(), hospital);
        Ambulance ambulance = requestDTO.isAmbulanceRequested()
                ? findAvailableAmbulance(hospital.getId())
                : null;

        if (ambulance != null) {
            ambulance.setAvailable(false);
            ambulanceRepository.save(ambulance);
        }

        Booking booking = new Booking();
        booking.setBookingType(requestDTO.getBookingType());
        booking.setStatus(CONFIRMED);
        booking.setAppointmentTime(requestDTO.getAppointmentTime());
        booking.setAmbulanceRequested(requestDTO.isAmbulanceRequested());
        booking.setUser(user);
        booking.setHospital(hospital);
        booking.setDoctor(doctor);
        booking.setAmbulance(ambulance);

        return toResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingResponseDTO getBookingById(Long bookingId) {
        return toResponse(findBooking(bookingId));
    }

    @Override
    @Transactional
    public List<BookingResponseDTO> getBookingsForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return bookingRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<BookingResponseDTO> getBookingsForHospital(Long hospitalId) {
        if (!hospitalRepository.existsById(hospitalId)) {
            throw new EntityNotFoundException("Hospital not found with id: " + hospitalId);
        }
        return bookingRepository.findByHospitalId(hospitalId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId) {
        Booking booking = findBooking(bookingId);
        if (CANCELLED.equals(booking.getStatus())) {
            throw new IllegalStateException("Booking is already cancelled: " + bookingId);
        }

        booking.setStatus(CANCELLED);
        Ambulance ambulance = booking.getAmbulance();
        if (ambulance != null) {
            ambulance.setAvailable(true);
            ambulanceRepository.save(ambulance);
        }

        return toResponse(bookingRepository.save(booking));
    }

    @Override
    @Transactional
    public BookingResponseDTO confirmBooking(Long bookingId) {
        Booking booking = findBooking(bookingId);
        if (CANCELLED.equals(booking.getStatus())) {
            throw new IllegalStateException("Cancelled bookings cannot be confirmed: " + bookingId);
        }

        booking.setStatus(CONFIRMED);
        return toResponse(bookingRepository.save(booking));
    }

    private Booking findBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Booking not found with id: " + bookingId));
    }

    private Doctor findDoctor(Long doctorId, Hospital hospital) {
        if (doctorId == null) {
            return null;
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Doctor not found with id: " + doctorId));
        if (!doctor.getHospital().getId().equals(hospital.getId())) {
            throw new IllegalArgumentException("Doctor does not belong to the selected hospital");
        }
        return doctor;
    }

    private Ambulance findAvailableAmbulance(Long hospitalId) {
        return ambulanceRepository.findByHospitalIdAndIsAvailableTrue(hospitalId).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No available ambulance found for hospital: " + hospitalId));
    }

    private BookingResponseDTO toResponse(Booking booking) {
        BookingResponseDTO response = modelMapper.map(booking, BookingResponseDTO.class);
        response.setUser(toUserDTO(booking.getUser()));
        response.setHospital(toHospitalDTO(booking.getHospital()));
        response.setDoctor(toDoctorDTO(booking.getDoctor()));
        response.setAmbulance(toAmbulanceDTO(booking.getAmbulance()));
        return response;
    }

    private UserDTO toUserDTO(AppUser user) {
        UserDTO response = modelMapper.map(user, UserDTO.class);
        response.setPassword(null);
        return response;
    }

    private HospitalDTO toHospitalDTO(Hospital hospital) {
        HospitalDTO response = modelMapper.map(hospital, HospitalDTO.class);
        response.setDoctors(null);
        response.setAmbulances(null);
        return response;
    }

    private DoctorDTO toDoctorDTO(Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        DoctorDTO response = modelMapper.map(doctor, DoctorDTO.class);
        response.setHospitalId(doctor.getHospital().getId());
        return response;
    }

    private AmbulanceDTO toAmbulanceDTO(Ambulance ambulance) {
        if (ambulance == null) {
            return null;
        }
        AmbulanceDTO response = modelMapper.map(ambulance, AmbulanceDTO.class);
        response.setHospitalId(ambulance.getHospital().getId());
        return response;
    }
}
