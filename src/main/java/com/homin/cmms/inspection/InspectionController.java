package com.homin.cmms.inspection;

import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.equipment.dto.EquipmentCreateRequest;
import com.homin.cmms.equipment.dto.EquipmentResponse;
import com.homin.cmms.inspection.dto.InspectionCreateRequest;
import com.homin.cmms.inspection.dto.InspectionResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/equipments/{equipmentId}/inspections")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionService = inspectionService;
    }

    @PostMapping
    public ResponseEntity<InspectionResponse> create(
            @PathVariable Long equipmentId,
            @Valid @RequestBody InspectionCreateRequest request
    ) {
        Inspection inspection = inspectionService.create(
                equipmentId,
                request.getInspectedAt(),
                request.getResult(),
                request.getContent()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InspectionResponse.from(inspection));
    }

    @GetMapping
    public List<InspectionResponse> findByEquipmentId(
            @PathVariable Long equipmentId
    ) {
        List<Inspection> inspections =
                inspectionService.findByEquipmentId(equipmentId);

        return inspections.stream()
                .map(InspectionResponse::from)
                .toList();
    }
}
