package com.narcissisticengineer.smartHospitalAvailabilitySystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AmbulanceDTO {

    private Long id;

    @NotEmpty(message = "Vehicle number cannot be empty.")
    private String vehicleNumber;

    @NotEmpty(message = "Driver's name cannot be empty.")
    private String driverName;

    private boolean isAvailable;

    @NotNull(message = "Hospital ID cannot be null.")
    private Long hospitalId;
}