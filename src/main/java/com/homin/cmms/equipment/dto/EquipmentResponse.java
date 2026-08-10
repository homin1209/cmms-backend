package com.homin.cmms.equipment.dto;

import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.equipment.EquipmentStatus;
import lombok.Getter;

@Getter
public class EquipmentResponse {

    private Long id;
    private String code;
    private String name;
    private EquipmentStatus status;

    public EquipmentResponse(Long id, String code, String name, EquipmentStatus status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public static EquipmentResponse from(Equipment equipment) {
        return new EquipmentResponse(
                equipment.getId(),
                equipment.getCode(),
                equipment.getName(),
                equipment.getStatus());
    }
}
