package com.homin.cmms.equipment.dto;

import com.homin.cmms.equipment.EquipmentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EquipmentCreateRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    private EquipmentStatus status;

    public EquipmentCreateRequest(String code, String name, EquipmentStatus status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }
}
