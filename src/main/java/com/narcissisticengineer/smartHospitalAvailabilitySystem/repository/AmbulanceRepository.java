package com.narcissisticengineer.smartHospitalAvailabilitySystem.repository;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {
    List<Ambulance> findByHospitalId(Long hospitalId);
}
