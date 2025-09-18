package com.narcissisticengineer.smartHospitalAvailabilitySystem.service;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.HospitalDTO;

import java.util.List;


public interface HospitalServices{
    HospitalDTO createHospital(HospitalDTO hospitalDTO);
    List<HospitalDTO> getAllHospitals();
    HospitalDTO getHospitalById(Long id);
    HospitalDTO updateHospital(Long id, HospitalDTO hospitalDTO);
    void deleteHospital(Long id);
}
