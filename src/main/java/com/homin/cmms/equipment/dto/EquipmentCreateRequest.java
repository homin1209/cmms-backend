package com.homin.cmms.equipment.dto;

import com.homin.cmms.equipment.EquipmentStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EquipmentCreateRequest {

    @NotBlank(message = "설비 코드는 필수입니다.")
    private String code;

    @NotBlank(message = "설비 이름은 필수입니다.")
    private String name;

    private EquipmentStatus status;

    public EquipmentCreateRequest(String code, String name, EquipmentStatus status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }
}
