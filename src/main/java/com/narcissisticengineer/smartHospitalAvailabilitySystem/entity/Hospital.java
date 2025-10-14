package com.narcissisticengineer.smartHospitalAvailabilitySystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hospital_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(name = "contact_number", nullable = false, unique = true)
    private String contactNumber;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "available_icu_beds")
    private int availableIcuBeds = 0;

    @Column(name = "available_general_beds")
    private int availableGeneralBeds = 0;

    @Column(name = "available_ventilator_beds")
    private int availableVentilatorBeds = 0;

    @Column(name = "available_doctors")
    private int availableDoctors = 0;

    @Column(name = "blood_a_positive")
    private int bloodAPositive = 0;

    @Column(name = "blood_a_negative")
    private int bloodANegative = 0;

    @Column(name = "blood_b_positive")
    private int bloodBPositive = 0;

    @Column(name = "blood_b_negative")
    private int bloodBNegative = 0;

    @Column(name = "blood_ab_positive")
    private int bloodAbPositive = 0;

    @Column(name = "blood_ab_negative")
    private int bloodAbNegative = 0;

    @Column(name = "blood_o_positive")
    private int bloodOPositive = 0;

    @Column(name = "blood_o_negative")
    private int bloodONegative = 0;

    @OneToMany(
            mappedBy = "hospital",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Doctor> doctors = new ArrayList<>();
}
