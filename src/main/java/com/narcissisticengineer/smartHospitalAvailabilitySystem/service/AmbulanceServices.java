package com.narcissisticengineer.smartHospitalAvailabilitySystem.service;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.AmbulanceDTO;
import java.util.List;

public interface AmbulanceServices {
    AmbulanceDTO addAmbulance(AmbulanceDTO ambulanceDTO);
    List<AmbulanceDTO> getAmbulancesByHospital(Long hospitalId);
    AmbulanceDTO updateAmbulanceAvailability(Long ambulanceId, boolean isAvailable);
    void deleteAmbulance(Long ambulanceId);
}
