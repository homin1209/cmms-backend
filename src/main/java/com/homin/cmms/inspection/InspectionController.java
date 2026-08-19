package com.homin.cmms.inspection;

import com.homin.cmms.inspection.dto.InspectionCreateRequest;
import com.homin.cmms.inspection.dto.InspectionResponse;
import com.homin.cmms.inspection.dto.InspectionUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public InspectionResponse findById(
            @PathVariable Long equipmentId,
            @PathVariable Long id
    ) {
        Inspection inspection = inspectionService.findById(equipmentId, id);

        return InspectionResponse.from(inspection);
    }

    @PutMapping("/{id}")
    public InspectionResponse update(
            @PathVariable Long equipmentId,
            @PathVariable Long id,
            @Valid @RequestBody InspectionUpdateRequest request
    ) {
        Inspection inspection = inspectionService.update(equipmentId, id, request.getInspectedAt(), request.getResult(), request.getContent());

        return InspectionResponse.from(inspection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long equipmentId,
            @PathVariable Long id
    ) {
        inspectionService.delete(equipmentId, id);

        return ResponseEntity.noContent().build();
    }
}
