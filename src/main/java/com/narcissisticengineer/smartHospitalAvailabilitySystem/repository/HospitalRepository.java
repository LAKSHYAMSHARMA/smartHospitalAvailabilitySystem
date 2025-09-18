package com.narcissisticengineer.smartHospitalAvailabilitySystem.repository;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long> {

}
