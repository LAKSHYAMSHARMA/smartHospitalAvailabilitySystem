package com.narcissisticengineer.smartHospitalAvailabilitySystem.controller;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.DoctorDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.DoctorServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorServices doctorService;

    public DoctorController(DoctorServices doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        return new ResponseEntity<>(doctorService.addDoctor(doctorDTO), HttpStatus.CREATED);
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsByHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(doctorService.getDoctorsByHospital(hospitalId));
    }

    @PutMapping("/{doctorId}/availability")
    public ResponseEntity<DoctorDTO> updateDoctorAvailability(@PathVariable Long doctorId, @RequestBody Map<String, Boolean> availability) {
        Boolean isAvailable = availability.get("isAvailable");
        return ResponseEntity.ok(doctorService.updateDoctorAvailability(doctorId, isAvailable));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}