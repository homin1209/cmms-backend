package com.homin.cmms.dashboard;

import com.homin.cmms.dashboard.dto.EquipmentStatisticsResponse;
import com.homin.cmms.dashboard.dto.FailureStatisticsResponse;
import com.homin.cmms.dashboard.dto.InspectionStatisticsResponse;
import com.homin.cmms.equipment.EquipmentRepository;
import com.homin.cmms.equipment.EquipmentStatus;
import com.homin.cmms.failure.FailureRepository;
import com.homin.cmms.failure.FailureStatus;
import com.homin.cmms.inspection.InspectionRepository;
import com.homin.cmms.inspection.InspectionResult;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final EquipmentRepository equipmentRepository;
    private final InspectionRepository inspectionRepository;
    private final FailureRepository failureRepository;

    public DashboardService(EquipmentRepository equipmentRepository, InspectionRepository inspectionRepository, FailureRepository failureRepository) {
        this.equipmentRepository = equipmentRepository;
        this.inspectionRepository = inspectionRepository;
        this.failureRepository = failureRepository;
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
}
