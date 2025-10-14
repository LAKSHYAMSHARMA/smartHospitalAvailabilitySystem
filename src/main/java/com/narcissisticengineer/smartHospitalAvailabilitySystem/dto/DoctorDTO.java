package com.narcissisticengineer.smartHospitalAvailabilitySystem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorDTO {

    private Long id;
    @NotEmpty(message = "Doctor's name cannot be empty.")
    private String name;
    @NotEmpty(message = "Doctor's specialization cannot be empty.")
    private String specialization;
    private boolean isAvailable;
    @NotNull(message = "Hospital ID is required to associate the doctor.")
    private Long hospitalId;

}
