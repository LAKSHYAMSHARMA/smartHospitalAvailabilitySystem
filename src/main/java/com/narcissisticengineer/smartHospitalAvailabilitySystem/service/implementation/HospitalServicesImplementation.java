package com.narcissisticengineer.smartHospitalAvailabilitySystem.service.implementation;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.HospitalDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Hospital;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.tools.exceptions.ResourceNotFoundException;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.repository.HospitalRepository;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.HospitalServices;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalServicesImplementation implements HospitalServices {

    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;


    public HospitalServicesImplementation(HospitalRepository hospitalRepository, ModelMapper modelMapper) {
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public HospitalDTO createHospital(HospitalDTO hospitalDTO) {
        Hospital hospital = modelMapper.map(hospitalDTO, Hospital.class);
        Hospital savedHospital = hospitalRepository.save(hospital);
        return modelMapper.map(savedHospital, HospitalDTO.class);
    }

    @Override
    public List<HospitalDTO> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        return hospitals.stream()
                .map(hospital -> modelMapper.map(hospital, HospitalDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public HospitalDTO getHospitalById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital", "id", id));
        return modelMapper.map(hospital, HospitalDTO.class);
    }

    @Override
    public HospitalDTO updateHospital(Long id, HospitalDTO hospitalDTO) {
        Hospital existingHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital", "id", id));

        existingHospital.setName(hospitalDTO.getName());
        existingHospital.setAddress(hospitalDTO.getAddress());
        existingHospital.setCity(hospitalDTO.getCity());
        existingHospital.setContactNumber(hospitalDTO.getContactNumber());
        existingHospital.setLatitude(hospitalDTO.getLatitude());
        existingHospital.setLongitude(hospitalDTO.getLongitude());

        existingHospital.setAvailableIcuBeds(hospitalDTO.getAvailableIcuBeds());
        existingHospital.setAvailableGeneralBeds(hospitalDTO.getAvailableGeneralBeds());
        existingHospital.setAvailableVentilatorBeds(hospitalDTO.getAvailableVentilatorBeds());
        existingHospital.setAvailableDoctors(hospitalDTO.getAvailableDoctors());

        existingHospital.setBloodAPositive(hospitalDTO.getBloodAPositive());
        existingHospital.setBloodANegative(hospitalDTO.getBloodANegative());
        existingHospital.setBloodBPositive(hospitalDTO.getBloodBPositive());
        existingHospital.setBloodBNegative(hospitalDTO.getBloodBNegative());
        existingHospital.setBloodAbPositive(hospitalDTO.getBloodAbPositive());
        existingHospital.setBloodAbNegative(hospitalDTO.getBloodAbNegative());
        existingHospital.setBloodOPositive(hospitalDTO.getBloodOPositive());
        existingHospital.setBloodONegative(hospitalDTO.getBloodONegative());


        Hospital updatedHospital = hospitalRepository.save(existingHospital);
        return modelMapper.map(updatedHospital, HospitalDTO.class);
    }

    @Override
    public void deleteHospital(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital", "id", id));
        hospitalRepository.delete(hospital);
    }
}
