package com.narcissisticengineer.smartHospitalAvailabilitySystem.service.implementation;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.AmbulanceDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Ambulance;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Hospital;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.repository.AmbulanceRepository;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.repository.HospitalRepository;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.AmbulanceServices;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbulanceServicesImplementation implements AmbulanceServices {

    private final AmbulanceRepository ambulanceRepository;
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;

    public AmbulanceServicesImplementation(AmbulanceRepository ambulanceRepository, HospitalRepository hospitalRepository, ModelMapper modelMapper) {
        this.ambulanceRepository = ambulanceRepository;
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AmbulanceDTO addAmbulance(AmbulanceDTO ambulanceDTO) {
        Long hospitalId = ambulanceDTO.getHospitalId();
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with id: " + hospitalId));

        Ambulance ambulance = modelMapper.map(ambulanceDTO, Ambulance.class);
        ambulance.setHospital(hospital);

        Ambulance savedAmbulance = ambulanceRepository.save(ambulance);
        return modelMapper.map(savedAmbulance, AmbulanceDTO.class);
    }

    @Override
    public List<AmbulanceDTO> getAmbulancesByHospital(Long hospitalId) {
        List<Ambulance> ambulances = ambulanceRepository.findByHospitalId(hospitalId);
        return ambulances.stream()
                .map(ambulance -> modelMapper.map(ambulance, AmbulanceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AmbulanceDTO updateAmbulanceAvailability(Long ambulanceId, boolean isAvailable) {
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new EntityNotFoundException("Ambulance not found with id: " + ambulanceId));
        ambulance.setAvailable(isAvailable);
        Ambulance updatedAmbulance = ambulanceRepository.save(ambulance);
        return modelMapper.map(updatedAmbulance, AmbulanceDTO.class);
    }

    @Override
    public void deleteAmbulance(Long ambulanceId) {
        if (!ambulanceRepository.existsById(ambulanceId)) {
            throw new EntityNotFoundException("Ambulance not found with id: " + ambulanceId);
        }
        ambulanceRepository.deleteById(ambulanceId);
    }
}