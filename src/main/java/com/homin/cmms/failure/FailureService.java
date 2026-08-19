package com.homin.cmms.failure;

import com.homin.cmms.common.exception.FailureNotFoundException;
import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.equipment.EquipmentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FailureService {

    private final FailureRepository failureRepository;
    private final EquipmentService equipmentService;

    public FailureService(FailureRepository failureRepository, EquipmentService equipmentService) {
        this.failureRepository = failureRepository;
        this.equipmentService = equipmentService;
    }

    public Failure create(Long equipmentId, LocalDateTime occurredAt, String description, FailureStatus status) {

        Equipment equipment = equipmentService.findById(equipmentId);

        Failure failure = new Failure(
                equipment,
                occurredAt,
                description,
                status
        );

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
    public Failure update(Long equipmentId, Long id, LocalDateTime occurredAt, String description, FailureStatus status) {
        Failure failure = findById(equipmentId, id);

        failure.update(occurredAt, description, status);

        return failure;
    }

    public void delete(Long equipmentId, Long id) {
        Failure failure = findById(equipmentId, id);

        failureRepository.delete(failure);
    }
}
