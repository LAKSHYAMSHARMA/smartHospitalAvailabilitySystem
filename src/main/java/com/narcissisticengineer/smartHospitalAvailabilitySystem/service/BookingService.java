package com.narcissisticengineer.smartHospitalAvailabilitySystem.service;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.BookingRequestDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.BookingResponseDTO;
import java.util.List;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO requestDTO);
    BookingResponseDTO getBookingById(Long bookingId);
    List<BookingResponseDTO> getBookingsForUser(Long userId);
    List<BookingResponseDTO> getBookingsForHospital(Long hospitalId);
    BookingResponseDTO cancelBooking(Long bookingId);
    BookingResponseDTO confirmBooking(Long bookingId);
}