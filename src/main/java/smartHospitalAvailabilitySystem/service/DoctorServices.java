package smartHospitalAvailabilitySystem.service;

import smartHospitalAvailabilitySystem.dto.DoctorDTO;
import java.util.List;

public interface DoctorServices {
    DoctorDTO addDoctor(DoctorDTO doctorDTO);
    List<DoctorDTO> getDoctorsByHospital(Long hospitalId);
    DoctorDTO updateDoctorAvailability(Long doctorId, boolean isAvailable);
    void deleteDoctor(Long doctorId);
}

