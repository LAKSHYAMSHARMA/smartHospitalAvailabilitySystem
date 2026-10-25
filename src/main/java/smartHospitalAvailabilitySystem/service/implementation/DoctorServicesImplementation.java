package smartHospitalAvailabilitySystem.service.implementation;

import smartHospitalAvailabilitySystem.dto.DoctorDTO;
import smartHospitalAvailabilitySystem.entity.Doctor;
import smartHospitalAvailabilitySystem.entity.Hospital;
import smartHospitalAvailabilitySystem.repository.DoctorRepository;
import smartHospitalAvailabilitySystem.repository.HospitalRepository;
import smartHospitalAvailabilitySystem.service.DoctorServices;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServicesImplementation implements DoctorServices {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;

    public DoctorServicesImplementation(DoctorRepository doctorRepository, HospitalRepository hospitalRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(value = "doctors", allEntries = true)
    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {

        Long hospitalId = doctorDTO.getHospitalId();

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with id: " + hospitalId));

        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        doctor.setHospital(hospital);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return modelMapper.map(savedDoctor, DoctorDTO.class);
    }

    @Override
    @Cacheable(value = "doctors", key = "#hospitalId")
    public List<DoctorDTO> getDoctorsByHospital(Long hospitalId) {
        List<Doctor> doctors = doctorRepository.findByHospitalId(hospitalId);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "doctors", allEntries = true)
    public DoctorDTO updateDoctorAvailability(Long doctorId, boolean isAvailable) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + doctorId));
        doctor.setAvailable(isAvailable);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(updatedDoctor, DoctorDTO.class);
    }

    @Override
    @CacheEvict(value = "doctors", allEntries = true)
    public void deleteDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found with id: " + doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }
}
