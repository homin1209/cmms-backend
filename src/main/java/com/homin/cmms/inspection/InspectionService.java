package com.homin.cmms.inspection;

import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.equipment.EquipmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final EquipmentService equipmentService;

    public InspectionService(InspectionRepository inspectionRepository, EquipmentService equipmentService) {
        this.inspectionRepository = inspectionRepository;
        this.equipmentService = equipmentService;
    }

    public Inspection create(Long equipmentId, LocalDateTime inspectedAt, InspectionResult result, String content) {

        Equipment equipment = equipmentService.findById(equipmentId);

        Inspection inspection = new Inspection(
                equipment,
                inspectedAt,
                result,
                content
        );

        return inspectionRepository.save(inspection);
    }

    public List<Inspection> findByEquipmentId(Long equipmentId) {

        equipmentService.findById(equipmentId);

        return inspectionRepository.findByEquipmentId(equipmentId);
    }
}
