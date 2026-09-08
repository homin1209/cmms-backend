package com.homin.cmms.equipment;

import com.homin.cmms.equipment.dto.EquipmentCreateRequest;
import com.homin.cmms.equipment.dto.EquipmentResponse;
import com.homin.cmms.equipment.dto.EquipmentUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipments")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public ResponseEntity<EquipmentResponse> create(
            @Valid @RequestBody EquipmentCreateRequest request
    ) {
        Equipment equipment;
        if (request.getStatus() == null) {
            equipment = equipmentService.create(
                    request.getCode(),
                    request.getName()
            );
        }
        else{
            equipment = equipmentService.create(
                    request.getCode(),
                    request.getName(),
                    request.getStatus()
            );
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EquipmentResponse.from(equipment));
    }

    @GetMapping
    public Page<EquipmentResponse> findAll(
            @RequestParam(required = false) EquipmentStatus status,
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        Page<Equipment> equipments = equipmentService.findAll(status, name, pageable);

        return equipments.map(EquipmentResponse::from);
    }

    @GetMapping("/{id}")
    public EquipmentResponse findById(@PathVariable Long id) {
        Equipment equipment = equipmentService.findById(id);

        return EquipmentResponse.from(equipment);
    }

    @PutMapping("/{id}")
    public EquipmentResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentUpdateRequest request
            ) {
        Equipment equipment = equipmentService.update(id, request.getCode(), request.getName());

        return EquipmentResponse.from(equipment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
