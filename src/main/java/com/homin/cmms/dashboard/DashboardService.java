package com.homin.cmms.dashboard;

import com.homin.cmms.dashboard.dto.EquipmentStatisticsResponse;
import com.homin.cmms.equipment.EquipmentRepository;
import com.homin.cmms.equipment.EquipmentStatus;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final EquipmentRepository equipmentRepository;

    public DashboardService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
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
}
