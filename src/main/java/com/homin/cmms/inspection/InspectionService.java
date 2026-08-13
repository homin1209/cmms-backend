package com.homin.cmms.inspection;

import com.homin.cmms.common.exception.InspectionNotFoundException;
import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.equipment.EquipmentService;
import jakarta.transaction.Transactional;
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

    public Inspection findById(Long equipmentId, Long id) {

        return inspectionRepository.findByIdAndEquipmentId(equipmentId, id)
                .orElseThrow(() -> new InspectionNotFoundException("존재하지 않는 점검 기록입니다."));
    }

    @Transactional
    public Inspection update(Long equipmentId, Long id, LocalDateTime inspectedAt, InspectionResult result, String content) {
        Inspection inspection = findById(equipmentId, id);

        inspection.update(inspectedAt, result, content);

        return inspection;
    }

    public void delete(Long equipmentId, Long id) {
        Inspection inspection = findById(equipmentId, id);

        inspectionRepository.delete(inspection);
    }
}
