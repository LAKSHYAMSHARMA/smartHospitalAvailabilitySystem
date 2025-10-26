package com.narcissisticengineer.smartHospitalAvailabilitySystem.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {

    private Long id;
    private String bookingType;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime appointmentTime;
    private boolean ambulanceRequested;

    private UserDTO user;
    private HospitalDTO hospital;
    private DoctorDTO doctor;
    private AmbulanceDTO ambulance;
}
