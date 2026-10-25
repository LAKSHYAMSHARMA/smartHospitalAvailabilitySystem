package smartHospitalAvailabilitySystem.service.implementation;

import smartHospitalAvailabilitySystem.dto.AmbulanceDTO;
import smartHospitalAvailabilitySystem.entity.Ambulance;
import smartHospitalAvailabilitySystem.entity.Hospital;
import smartHospitalAvailabilitySystem.repository.AmbulanceRepository;
import smartHospitalAvailabilitySystem.repository.HospitalRepository;
import smartHospitalAvailabilitySystem.service.AmbulanceServices;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(value = "ambulances", allEntries = true)
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
    @Cacheable(value = "ambulances", key = "#hospitalId")
    public List<AmbulanceDTO> getAmbulancesByHospital(Long hospitalId) {
        List<Ambulance> ambulances = ambulanceRepository.findByHospitalId(hospitalId);
        return ambulances.stream()
                .map(ambulance -> modelMapper.map(ambulance, AmbulanceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "ambulances", allEntries = true)
    public AmbulanceDTO updateAmbulanceAvailability(Long ambulanceId, boolean isAvailable) {
        Ambulance ambulance = ambulanceRepository.findById(ambulanceId)
                .orElseThrow(() -> new EntityNotFoundException("Ambulance not found with id: " + ambulanceId));
        ambulance.setAvailable(isAvailable);
        Ambulance updatedAmbulance = ambulanceRepository.save(ambulance);
        return modelMapper.map(updatedAmbulance, AmbulanceDTO.class);
    }

    @Override
    @CacheEvict(value = "ambulances", allEntries = true)
    public void deleteAmbulance(Long ambulanceId) {
        if (!ambulanceRepository.existsById(ambulanceId)) {
            throw new EntityNotFoundException("Ambulance not found with id: " + ambulanceId);
        }
        ambulanceRepository.deleteById(ambulanceId);
    }
}