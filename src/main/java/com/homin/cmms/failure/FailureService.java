package com.homin.cmms.failure;

import com.homin.cmms.common.exception.FailureNotFoundException;
import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.equipment.EquipmentService;
import com.homin.cmms.equipment.EquipmentStatus;
import com.homin.cmms.inspection.Inspection;
import com.homin.cmms.inspection.InspectionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FailureService {

    private final FailureRepository failureRepository;
    private final EquipmentService equipmentService;
    private final InspectionService inspectionService;

    public FailureService(FailureRepository failureRepository, EquipmentService equipmentService, InspectionService inspectionService) {
        this.failureRepository = failureRepository;
        this.equipmentService = equipmentService;
        this.inspectionService = inspectionService;
    }

    @Transactional
    public Failure create(Long equipmentId, Long inspectionId, LocalDateTime occurredAt, String description, FailureStatus status) {

        Equipment equipment = equipmentService.findById(equipmentId);

        Inspection inspection = null;

        if (inspectionId != null) {
            inspection = inspectionService.findById(equipmentId, inspectionId);
        }

        Failure failure = new Failure(
                equipment,
                inspection,
                occurredAt,
                description,
                status
        );

        equipment.changeStatus(EquipmentStatus.FAILURE);

        return failureRepository.save(failure);
    }

    public List<Failure> findByEquipmentId(Long equipmentId) {

        equipmentService.findById(equipmentId);

        return failureRepository.findByEquipmentId(equipmentId);
    }

    public Failure findById(Long equipmentId, Long id) {

        return failureRepository.findByEquipmentIdAndId(equipmentId, id)
                .orElseThrow(() -> new FailureNotFoundException("존재하지 않는 고장 이력입니다."));
    }

    @Transactional
    public Failure update(Long equipmentId, Long id, Long inspectionId, LocalDateTime occurredAt, String description, FailureStatus status) {
        Failure failure = findById(equipmentId, id);

        Inspection inspection = null;

        if (inspectionId != null) {
            inspection = inspectionService.findById(equipmentId, inspectionId);
        }

        failure.update(inspection, occurredAt, description, status);

        return failure;
    }

    public void delete(Long equipmentId, Long id) {
        Failure failure = findById(equipmentId, id);

        failureRepository.delete(failure);
    }
}
