package com.homin.cmms.dashboard;

import com.homin.cmms.dashboard.dto.*;
import com.homin.cmms.equipment.EquipmentRepository;
import com.homin.cmms.equipment.EquipmentStatus;
import com.homin.cmms.failure.FailureRepository;
import com.homin.cmms.failure.FailureStatus;
import com.homin.cmms.inspection.InspectionRepository;
import com.homin.cmms.inspection.InspectionResult;
import com.homin.cmms.maintenance.MaintenanceRepository;
import com.homin.cmms.maintenance.MaintenanceStatus;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final EquipmentRepository equipmentRepository;
    private final InspectionRepository inspectionRepository;
    private final FailureRepository failureRepository;
    private final MaintenanceRepository maintenanceRepository;

    public DashboardService(EquipmentRepository equipmentRepository, InspectionRepository inspectionRepository, FailureRepository failureRepository, MaintenanceRepository maintenanceRepository) {
        this.equipmentRepository = equipmentRepository;
        this.inspectionRepository = inspectionRepository;
        this.failureRepository = failureRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    public EquipmentStatisticsResponse getEquipmentStatistics() {
        long total = equipmentRepository.count();
        long running = equipmentRepository.countByStatus(EquipmentStatus.RUNNING);
        long failure = equipmentRepository.countByStatus(EquipmentStatus.FAILURE);
        long maintenance = equipmentRepository.countByStatus(EquipmentStatus.MAINTENANCE);

        return new EquipmentStatisticsResponse(
                total,
                running,
                failure,
                maintenance
        );
    }

    public InspectionStatisticsResponse getInspectionStatistics() {
        long normal = inspectionRepository.countByResult(InspectionResult.NORMAL);
        long abnormal = inspectionRepository.countByResult(InspectionResult.ABNORMAL);

        return new InspectionStatisticsResponse(
                normal,
                abnormal
        );
    }

    public FailureStatisticsResponse getFailureStatistics() {
        long reported = failureRepository.countByStatus(FailureStatus.REPORTED);
        long inProgress = failureRepository.countByStatus(FailureStatus.IN_PROGRESS);
        long resolved = failureRepository.countByStatus(FailureStatus.RESOLVED);

        return new FailureStatisticsResponse(
                reported,
                inProgress,
                resolved
        );
    }

    public MaintenanceStatisticsResponse getMaintenanceStatistics() {
        long planned = maintenanceRepository.countByStatus(MaintenanceStatus.PLANNED);
        long inProgress = maintenanceRepository.countByStatus(MaintenanceStatus.IN_PROGRESS);
        long completed = maintenanceRepository.countByStatus(MaintenanceStatus.COMPLETED);

        return new MaintenanceStatisticsResponse(
                planned,
                inProgress,
                completed
        );
    }

    public DashboardResponse getDashboard() {
        EquipmentStatisticsResponse equipment = getEquipmentStatistics();
        InspectionStatisticsResponse inspection = getInspectionStatistics();
        FailureStatisticsResponse failure = getFailureStatistics();
        MaintenanceStatisticsResponse maintenance = getMaintenanceStatistics();

        return new DashboardResponse(
                equipment,
                inspection,
                failure,
                maintenance
        );
    }
}
