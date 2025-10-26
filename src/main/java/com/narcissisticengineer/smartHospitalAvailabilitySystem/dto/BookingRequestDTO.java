package com.narcissisticengineer.smartHospitalAvailabilitySystem.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequestDTO {

    @NotEmpty(message = "Booking type is required (e.g., BED, APPOINTMENT, EMERGENCY).")
    private String bookingType;

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Hospital ID is required.")
    private Long hospitalId;

    private Long doctorId;

    @Future(message = "Appointment time must be in the future.")

    private LocalDateTime appointmentTime;

    private boolean ambulanceRequested = false;
}
