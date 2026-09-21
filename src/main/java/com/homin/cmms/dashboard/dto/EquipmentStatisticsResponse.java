package com.homin.cmms.dashboard.dto;

import lombok.Getter;

@Getter
public class EquipmentStatisticsResponse {

    private final long total;
    private final long running;
    private final long failure;
    private final long maintenance;

    public EquipmentStatisticsResponse(long total, long running, long failure, long maintenance) {
        this.total = total;
        this.running = running;
        this.failure = failure;
        this.maintenance = maintenance;
    }
}
