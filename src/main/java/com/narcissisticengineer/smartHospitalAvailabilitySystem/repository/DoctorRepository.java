package com.narcissisticengineer.smartHospitalAvailabilitySystem.repository;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByHospitalId(Long hospitalId);
}

