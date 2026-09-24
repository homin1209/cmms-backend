package com.homin.cmms.dashboard;

import com.homin.cmms.dashboard.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/equipments")
    public ResponseEntity<EquipmentStatisticsResponse> getEquipmentStatistics() {
        EquipmentStatisticsResponse response = dashboardService.getEquipmentStatistics();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/inspections")
    public ResponseEntity<InspectionStatisticsResponse> getInspectionStatistics() {
        InspectionStatisticsResponse response = dashboardService.getInspectionStatistics();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/failures")
    public ResponseEntity<FailureStatisticsResponse> getFailureStatistics() {
        FailureStatisticsResponse response = dashboardService.getFailureStatistics();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/maintenances")
    public ResponseEntity<MaintenanceStatisticsResponse> getMaintenanceStatistics() {

        MaintenanceStatisticsResponse response = dashboardService.getMaintenanceStatistics();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {

        DashboardResponse response = dashboardService.getDashboard();

        return ResponseEntity.ok(response);
    }
}
