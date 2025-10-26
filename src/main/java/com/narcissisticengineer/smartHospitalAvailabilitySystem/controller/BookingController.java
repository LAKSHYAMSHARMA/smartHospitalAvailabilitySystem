package com.narcissisticengineer.smartHospitalAvailabilitySystem.controller;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.BookingRequestDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.BookingResponseDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {
        BookingResponseDTO response = bookingService.createBooking(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsForUser(userId));
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsForHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(bookingService.getBookingsForHospital(hospitalId));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.confirmBooking(id));
    }
}