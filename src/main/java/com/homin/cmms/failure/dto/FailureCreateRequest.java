package com.homin.cmms.failure.dto;

import com.homin.cmms.failure.FailureStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FailureCreateRequest {

    @NotNull(message = "고장 발생 일자는 필수입니다.")
    private LocalDateTime occurredAt;

    @NotBlank(message = "고장 내용은 필수입니다.")
    private String description;

    @NotNull(message = "고장 상태는 필수입니다.")
    private FailureStatus status;

    public FailureCreateRequest(LocalDateTime occurredAt, String description, FailureStatus status) {
        this.occurredAt = occurredAt;
        this.description = description;
        this.status = status;
    }
}
