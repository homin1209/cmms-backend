package com.homin.cmms.equipment;

import com.homin.cmms.equipment.dto.EquipmentCreateRequest;
import com.homin.cmms.equipment.dto.EquipmentResponse;
import com.homin.cmms.equipment.dto.EquipmentUpdateRequest;
import jakarta.validation.Valid;
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
    public EquipmentResponse create(
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

        return EquipmentResponse.from(equipment);
    }

    @GetMapping
    public List<EquipmentResponse> findAll() {
        List<Equipment> equipments = equipmentService.findAll();

        return equipments.stream()
                .map(EquipmentResponse::from)
                .toList();
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
        Equipment equipment = equipmentService.update(id, request.getCode(), request.getName(), request.getStatus());

        return EquipmentResponse.from(equipment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        equipmentService.delete(id);
    }
}
