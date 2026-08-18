package com.homin.cmms.maintenance.dto;

import com.homin.cmms.failure.FailureStatus;
import com.homin.cmms.maintenance.MaintenanceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MaintenanceCreateRequest {

    private Long failureId;

    @NotNull(message = "정비 일시는 필수입니다.")
    private LocalDateTime performedAt;

    @NotBlank(message = "정비 내용은 필수입니다.")
    private String description;

    @NotNull(message = "정비 상태는 필수입니다.")
    private MaintenanceStatus status;

    public MaintenanceCreateRequest(Long failureId, LocalDateTime performedAt, String description, MaintenanceStatus status) {
        this.failureId = failureId;
        this.performedAt = performedAt;
        this.description = description;
        this.status = status;
    }
}
