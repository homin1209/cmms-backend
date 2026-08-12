package com.homin.cmms.inspection.dto;

import com.homin.cmms.inspection.Inspection;
import com.homin.cmms.inspection.InspectionResult;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InspectionResponse {

    private Long id;
    private Long equipmentId;
    private LocalDateTime inspectedAt;
    private InspectionResult result;
    private String content;

    public InspectionResponse(Long id, Long equipmentId, LocalDateTime inspectedAt, InspectionResult result, String content) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.inspectedAt = inspectedAt;
        this.result = result;
        this.content = content;
    }

    public static InspectionResponse from(Inspection inspection) {
        return new InspectionResponse(
                inspection.getId(),
                inspection.getEquipment().getId(),
                inspection.getInspectedAt(),
                inspection.getResult(),
                inspection.getContent()
        );
    }
}
