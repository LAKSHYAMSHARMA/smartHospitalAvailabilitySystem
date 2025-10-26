package com.narcissisticengineer.smartHospitalAvailabilitySystem.repository;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByHospitalId(Long hospitalId);
}
