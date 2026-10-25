package smartHospitalAvailabilitySystem.controller;

import smartHospitalAvailabilitySystem.dto.AmbulanceDTO;
import smartHospitalAvailabilitySystem.service.AmbulanceServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ambulances")
public class AmbulanceController {

    private final AmbulanceServices ambulanceService;

    public AmbulanceController(AmbulanceServices ambulanceService) {
        this.ambulanceService = ambulanceService;
    }

    @PutMapping("/{ambulanceId}/availability")
    public ResponseEntity<AmbulanceDTO> updateAmbulanceAvailability(
            @PathVariable Long ambulanceId,
            @RequestBody Map<String, Boolean> availability) {
        return ResponseEntity.ok(ambulanceService.updateAmbulanceAvailability(
                ambulanceId,
                availability.get("isAvailable")));
    }

    @DeleteMapping("/{ambulanceId}")
    public ResponseEntity<Void> deleteAmbulance(@PathVariable Long ambulanceId) {
        ambulanceService.deleteAmbulance(ambulanceId);
        return ResponseEntity.noContent().build();
    }
}
