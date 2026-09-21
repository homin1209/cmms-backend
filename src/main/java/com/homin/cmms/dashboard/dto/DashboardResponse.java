package com.homin.cmms.dashboard.dto;

import lombok.Getter;

@Getter
public class DashboardResponse {

    private final EquipmentStatisticsResponse equipment;
    private final InspectionStatisticsResponse inspection;
    private final FailureStatisticsResponse failure;
    private final MaintenanceStatisticsResponse maintenance;

    public DashboardResponse(
            EquipmentStatisticsResponse equipment,
            InspectionStatisticsResponse inspection,
            FailureStatisticsResponse failure,
            MaintenanceStatisticsResponse maintenance
    ) {
        this.equipment = equipment;
        this.inspection = inspection;
        this.failure = failure;
        this.maintenance = maintenance;
    }
}
