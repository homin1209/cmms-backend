package com.homin.cmms.failure.dto;

import com.homin.cmms.failure.Failure;
import com.homin.cmms.failure.FailureStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FailureResponse {

    private Long id;
    private Long equipmentId;
    private Long inspectionId;
    private LocalDateTime occurredAt;
    private String description;
    private FailureStatus status;

    public FailureResponse(Long id, Long equipmentId, Long inspectionId, LocalDateTime occurredAt, String description, FailureStatus status) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.inspectionId = inspectionId;
        this.occurredAt = occurredAt;
        this.description = description;
        this.status = status;
    }

    public static FailureResponse from(Failure failure) {
        return new FailureResponse(
                failure.getId(),
                failure.getEquipment().getId(),
                failure.getInspection() != null
                        ? failure.getInspection().getId()
                        : null,
                failure.getOccurredAt(),
                failure.getDescription(),
                failure.getStatus()
        );
    }
}

