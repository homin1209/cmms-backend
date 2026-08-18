package com.homin.cmms.maintenance.dto;

import com.homin.cmms.failure.Failure;
import com.homin.cmms.failure.FailureStatus;
import com.homin.cmms.failure.dto.FailureResponse;
import com.homin.cmms.maintenance.Maintenance;
import com.homin.cmms.maintenance.MaintenanceStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MaintenanceResponse {

    private Long id;
    private Long equipmentId;
    private Long failureId;
    private LocalDateTime performedAt;
    private String description;
    private MaintenanceStatus status;

    public MaintenanceResponse(Long id, Long equipmentId, Long failureId, LocalDateTime performedAt, String description, MaintenanceStatus status) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.failureId = failureId;
        this.performedAt = performedAt;
        this.description = description;
        this.status = status;
    }

    public static MaintenanceResponse from(Maintenance maintenance) {
        return new MaintenanceResponse(
                maintenance.getId(),
                maintenance.getEquipment().getId(),
                maintenance.getFailure() != null
                    ? maintenance.getFailure().getId()
                    : null,
                maintenance.getPerformedAt(),
                maintenance.getDescription(),
                maintenance.getStatus()
        );
    }
}
