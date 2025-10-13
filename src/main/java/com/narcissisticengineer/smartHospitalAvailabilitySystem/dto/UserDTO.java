package com.narcissisticengineer.smartHospitalAvailabilitySystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Name cannot be empty.")
    @Size(min = 3, message = "Name must be at least 3 characters long.")
    private String name;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email format is invalid.")
    private String email;

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    private String phoneNumber;

    private Double homeLatitude;

    private Double homeLongitude;

}