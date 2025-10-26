package com.narcissisticengineer.smartHospitalAvailabilitySystem.service.implementation;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.AmbulanceDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.DoctorDTO;
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

        modelMapper.map(hospitalDTO, existingHospital);

        Hospital updatedHospital = hospitalRepository.save(existingHospital);

        return convertToDto(updatedHospital);
    }

    @Override
    public void deleteHospital(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital", "id", id));
        hospitalRepository.delete(hospital);
    }

    private HospitalDTO convertToDto(Hospital hospital) {
        HospitalDTO hospitalDTO = modelMapper.map(hospital, HospitalDTO.class);

        if (hospital.getDoctors() != null) {
            List<DoctorDTO> doctorDTOs = hospital.getDoctors().stream()
                    .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                    .collect(Collectors.toList());
            hospitalDTO.setDoctors(doctorDTOs);
        }

        if (hospital.getAmbulances() != null) {
            List<AmbulanceDTO> ambulanceDTOs = hospital.getAmbulances().stream()
                    .map(ambulance -> modelMapper.map(ambulance, AmbulanceDTO.class))
                    .collect(Collectors.toList());
            hospitalDTO.setAmbulances(ambulanceDTOs);
        }

        return hospitalDTO;
    }
}
