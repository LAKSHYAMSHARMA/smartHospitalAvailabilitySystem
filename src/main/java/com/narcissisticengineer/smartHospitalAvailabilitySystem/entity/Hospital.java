package com.narcissisticengineer.smartHospitalAvailabilitySystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long HospitalId;

    private String HospitalName;

    private String HospitalType;

    private long NumberOfBedAvailable;

    private double Location;
}
