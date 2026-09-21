package com.homin.cmms.dashboard.dto;

import lombok.Getter;

@Getter
public class InspectionStatisticsResponse {

    private final long normal;
    private final long abnormal;

    public InspectionStatisticsResponse(long normal, long abnormal) {
        this.normal = normal;
        this.abnormal = abnormal;
    }
}
