package com.homin.cmms.dashboard.dto;

import lombok.Getter;

@Getter
public class MaintenanceStatisticsResponse {

    private final long planned;
    private final long inProgress;
    private final long completed;

    public MaintenanceStatisticsResponse(long planned, long inProgress, long completed) {
        this.planned = planned;
        this.inProgress = inProgress;
        this.completed = completed;
    }
}
