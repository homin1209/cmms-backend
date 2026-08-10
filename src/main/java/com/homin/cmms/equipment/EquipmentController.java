package com.homin.cmms.equipment;

import com.homin.cmms.equipment.dto.EquipmentCreateRequest;
import com.homin.cmms.equipment.dto.EquipmentResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
