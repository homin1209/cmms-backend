package com.homin.cmms.maintenance;

import com.homin.cmms.maintenance.dto.MaintenanceCreateRequest;
import com.homin.cmms.maintenance.dto.MaintenanceResponse;
import com.homin.cmms.maintenance.dto.MaintenanceUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipments/{equipmentId}/maintenances")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponse> create(
            @PathVariable Long equipmentId,
            @Valid @RequestBody MaintenanceCreateRequest request
    ) {
        Maintenance maintenance = maintenanceService.create(
                equipmentId,
                request.getFailureId(),
                request.getPerformedAt(),
                request.getDescription(),
                request.getStatus()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(MaintenanceResponse.from(maintenance));
    }

    @GetMapping
    public List<MaintenanceResponse> findByEquipmentId(
            @PathVariable Long equipmentId
    ) {
        List<Maintenance> maintenances =
                maintenanceService.findByEquipmentId(equipmentId);

        return maintenances.stream()
                .map(MaintenanceResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public MaintenanceResponse findById(
            @PathVariable Long equipmentId,
            @PathVariable Long id
    ) {
        Maintenance maintenance = maintenanceService.findById(equipmentId, id);

        return MaintenanceResponse.from(maintenance);
    }

    @PutMapping("/{id}")
    public MaintenanceResponse update(
            @PathVariable Long equipmentId,
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceUpdateRequest request
    ) {
        Maintenance maintenance = maintenanceService.update(equipmentId, id, request.getFailureId(), request.getPerformedAt(), request.getDescription(), request.getStatus());

        return MaintenanceResponse.from(maintenance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long equipmentId,
            @PathVariable Long id
    ) {
        maintenanceService.delete(equipmentId, id);

        return ResponseEntity.noContent().build();
    }
}
