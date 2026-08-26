package com.homin.cmms.maintenance;

import com.homin.cmms.common.exception.MaintenanceNotFoundException;
import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.equipment.EquipmentService;
import com.homin.cmms.equipment.EquipmentStatus;
import com.homin.cmms.failure.Failure;
import com.homin.cmms.failure.FailureService;
import com.homin.cmms.failure.FailureStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentService equipmentService;
    private final FailureService failureService;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, EquipmentService equipmentService, FailureService failureService) {
        this.maintenanceRepository = maintenanceRepository;
        this.equipmentService = equipmentService;
        this.failureService = failureService;
    }

    @Transactional
    public Maintenance create(Long equipmentId, Long failureId, LocalDateTime performedAt, String description, MaintenanceStatus status) {
        Equipment equipment = equipmentService.findById(equipmentId);
        Failure failure = null;

        if (failureId != null) {
            failure = failureService.findById(equipmentId, failureId);
        }

        Maintenance maintenance = new Maintenance(equipment, failure, performedAt, description, status);

        synchronizeStatus(equipment, failure, status);

        return maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> findByEquipmentId(Long equipmentId) {

        equipmentService.findById(equipmentId);

        return maintenanceRepository.findByEquipmentId(equipmentId);
    }

    public Maintenance findById(Long equipmentId, Long id) {

        return maintenanceRepository.findByEquipmentIdAndId(equipmentId, id)
                .orElseThrow(() -> new MaintenanceNotFoundException("존재하지 않는 정비 이력입니다."));
    }

    @Transactional
    public Maintenance update(Long equipmentId, Long id, Long failureId, LocalDateTime performedAt, String description, MaintenanceStatus status) {

        Equipment equipment = equipmentService.findById(equipmentId);

        Maintenance maintenance = findById(equipmentId, id);

        Failure failure = null;

        if (failureId != null) {
            failure = failureService.findById(equipmentId, failureId);
        }

        maintenance.update(failure, performedAt, description, status);

        synchronizeStatus(equipment, failure, status);

        return maintenance;
    }

    public void delete(Long equipmentId, Long id) {

        Maintenance maintenance = findById(equipmentId, id);

        maintenanceRepository.delete(maintenance);
    }

    private void synchronizeStatus(Equipment equipment, Failure failure, MaintenanceStatus status) {
        if (status == MaintenanceStatus.IN_PROGRESS) {
            equipment.changeStatus(EquipmentStatus.MAINTENANCE);
        }

        if (status == MaintenanceStatus.COMPLETED) {
            equipment.changeStatus(EquipmentStatus.RUNNING);
        }

        if (failure != null && status == MaintenanceStatus.IN_PROGRESS) {
            failure.changeStatus(FailureStatus.IN_PROGRESS);
        }

        if (failure != null && status == MaintenanceStatus.COMPLETED) {
            failure.changeStatus(FailureStatus.RESOLVED);
        }
    }
}
