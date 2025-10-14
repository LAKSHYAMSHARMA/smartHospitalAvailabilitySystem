package com.narcissisticengineer.smartHospitalAvailabilitySystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDTO {

    private Long id;


    @NotEmpty(message = "Hospital name cannot be empty")
    @Size(min = 3, message = "Hospital name must be at least 3 characters long")
    private String name;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotEmpty(message = "Contact number cannot be empty")
    @Size(min = 10, max = 15, message = "Contact number must be between 10 and 15 digits")
    private String contactNumber;

    // --- Geographic Coordinates ---
    @NotNull(message = "Latitude cannot be null")
    private Double latitude;

    @NotNull(message = "Longitude cannot be null")
    private Double longitude;


    @NotNull(message = "ICU bed count cannot be null")
    @Min(value = 0, message = "Bed count cannot be negative")
    private int availableIcuBeds;

    @NotNull(message = "General bed count cannot be null")
    @Min(value = 0, message = "Bed count cannot be negative")
    private int availableGeneralBeds;

    @NotNull(message = "Ventilator bed count cannot be null")
    @Min(value = 0, message = "Bed count cannot be negative")
    private int availableVentilatorBeds;

    @NotNull(message = "Doctor count cannot be null")
    @Min(value = 0, message = "Doctor count cannot be negative")
    private int availableDoctors;

    @NotNull @Min(0) private int bloodAPositive;
    @NotNull @Min(0) private int bloodANegative;
    @NotNull @Min(0) private int bloodBPositive;
    @NotNull @Min(0) private int bloodBNegative;
    @NotNull @Min(0) private int bloodAbPositive;
    @NotNull @Min(0) private int bloodAbNegative;
    @NotNull @Min(0) private int bloodOPositive;
    @NotNull @Min(0) private int bloodONegative;

    private List<DoctorDTO> doctors;
}