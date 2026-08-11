package com.homin.cmms.equipment.dto;

import com.homin.cmms.equipment.EquipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EquipmentUpdateRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    private EquipmentStatus status;

    public EquipmentUpdateRequest(String code, String name, EquipmentStatus status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }
}
