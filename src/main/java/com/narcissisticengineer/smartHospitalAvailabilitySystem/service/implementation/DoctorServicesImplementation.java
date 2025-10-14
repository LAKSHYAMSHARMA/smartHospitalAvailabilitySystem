package com.narcissisticengineer.smartHospitalAvailabilitySystem.service.implementation;

import com.narcissisticengineer.smartHospitalAvailabilitySystem.dto.DoctorDTO;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Doctor;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.entity.Hospital;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.repository.DoctorRepository;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.repository.HospitalRepository;
import com.narcissisticengineer.smartHospitalAvailabilitySystem.service.DoctorServices;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServicesImplementation implements DoctorServices {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;

    public DoctorServicesImplementation(DoctorRepository doctorRepository, HospitalRepository hospitalRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DoctorDTO addDoctor(DoctorDTO doctorDTO) {

        Long hospitalId = doctorDTO.getHospitalId();

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with id: " + hospitalId));

        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

        doctor.setHospital(hospital);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return modelMapper.map(savedDoctor, DoctorDTO.class);
    }

    @Override
    public List<DoctorDTO> getDoctorsByHospital(Long hospitalId) {
        List<Doctor> doctors = doctorRepository.findByHospitalId(hospitalId);
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDTO updateDoctorAvailability(Long doctorId, boolean isAvailable) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + doctorId));
        doctor.setAvailable(isAvailable);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(updatedDoctor, DoctorDTO.class);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new EntityNotFoundException("Doctor not found with id: " + doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }
}
