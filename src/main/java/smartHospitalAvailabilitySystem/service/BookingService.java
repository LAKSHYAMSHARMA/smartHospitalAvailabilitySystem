package smartHospitalAvailabilitySystem.service;

import smartHospitalAvailabilitySystem.dto.BookingRequestDTO;
import smartHospitalAvailabilitySystem.dto.BookingResponseDTO;
import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO requestDTO);
    BookingResponseDTO getBookingById(Long bookingId);
    List<BookingResponseDTO> getBookingsForUser(Long userId);
    List<BookingResponseDTO> getBookingsForHospital(Long hospitalId);
    BookingResponseDTO cancelBooking(Long bookingId);
    BookingResponseDTO confirmBooking(Long bookingId);
}