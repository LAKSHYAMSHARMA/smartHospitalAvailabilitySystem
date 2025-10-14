package com.narcissisticengineer.smartHospitalAvailabilitySystem.controller;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.DoctorDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.HospitalDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.DoctorServices;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.HospitalServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalServices hospitalService;
    private final DoctorServices doctorService;

    public HospitalController(HospitalServices hospitalService, DoctorServices doctorService) {
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<HospitalDTO> createHospital(@Valid @RequestBody HospitalDTO hospitalDTO) {
        HospitalDTO createdHospital = hospitalService.createHospital(hospitalDTO);
        return new ResponseEntity<>(createdHospital, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<HospitalDTO>> getAllHospitals() {
        List<HospitalDTO> hospitals = hospitalService.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long id) {
        HospitalDTO hospital = hospitalService.getHospitalById(id);
        return ResponseEntity.ok(hospital);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(@PathVariable Long id, @Valid @RequestBody HospitalDTO hospitalDTO) {
        HospitalDTO updatedHospital = hospitalService.updateHospital(id, hospitalDTO);
        return ResponseEntity.ok(updatedHospital);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{hospitalId}/doctors")
    public ResponseEntity<DoctorDTO> addDoctorToHospital(
            @PathVariable Long hospitalId,
            @Valid @RequestBody DoctorDTO doctorDTO) {
        doctorDTO.setHospitalId(hospitalId);
        DoctorDTO createdDoctor = doctorService.addDoctor(doctorDTO);
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    @GetMapping("/{hospitalId}/doctors")
    public ResponseEntity<List<DoctorDTO>> getDoctorsForHospital(@PathVariable Long hospitalId) {

        List<DoctorDTO> doctors = doctorService.getDoctorsByHospital(hospitalId);
        return ResponseEntity.ok(doctors);
    }
}