package com.homin.cmms.inspection.dto;

import com.homin.cmms.inspection.InspectionResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InspectionUpdateRequest {

    @NotNull(message = "점검 일시는 필수입니다.")
    private LocalDateTime inspectedAt;

    @NotNull(message = "점검 결과는 필수입니다.")
    private InspectionResult result;

    @NotBlank(message = "점검 내용은 필수입니다.")
    private String content;

    public InspectionUpdateRequest(
            LocalDateTime inspectedAt,
            InspectionResult result,
            String content
    ) {
        this.inspectedAt = inspectedAt;
        this.result = result;
        this.content = content;
    }
}