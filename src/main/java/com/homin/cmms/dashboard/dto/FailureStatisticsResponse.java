package com.homin.cmms.dashboard.dto;

import lombok.Getter;

@Getter
public class FailureStatisticsResponse {

    private final long reported;
    private final long inProgress;
    private final long resolved;

    public FailureStatisticsResponse(long reported, long inProgress, long resolved) {
        this.reported = reported;
        this.inProgress = inProgress;
        this.resolved = resolved;
    }
}
