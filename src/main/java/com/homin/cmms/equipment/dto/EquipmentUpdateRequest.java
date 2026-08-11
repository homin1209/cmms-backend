package com.homin.cmms.equipment.dto;

import com.homin.cmms.equipment.EquipmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EquipmentUpdateRequest {

    @NotBlank(message = "설비 코드는 필수입니다.")
    private String code;

    @NotBlank(message = "설비 이름은 필수입니다.")
    private String name;

    @NotNull(message = "설비 상태는 필수입니다.")
    private EquipmentStatus status;

    public EquipmentUpdateRequest(String code, String name, EquipmentStatus status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }
}
