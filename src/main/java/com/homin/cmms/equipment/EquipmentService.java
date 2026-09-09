package com.homin.cmms.equipment;

import com.homin.cmms.common.exception.DuplicateEquipmentCodeException;
import com.homin.cmms.common.exception.EquipmentNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Equipment create(String code, String name) {
        return create(code, name, EquipmentStatus.RUNNING);
    }

    public Equipment create(String code, String name, EquipmentStatus status) {
        if (equipmentRepository.existsByCode(code)) {
            throw new DuplicateEquipmentCodeException("이미 존재하는 설비 코드입니다.");
        }

        Equipment equipment = new Equipment(code, name, status);

        return equipmentRepository.save(equipment);
    }

    public Page<Equipment> findAll(EquipmentStatus status, String name, Pageable pageable) {
        if (status != null && name != null) {
            return equipmentRepository.findByStatusAndNameContainingIgnoreCase(status, name, pageable);
        }

        if (status != null) {
            return equipmentRepository.findByStatus(status, pageable);
        }

        if (name != null) {
            return equipmentRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        return equipmentRepository.findAll(pageable);
    }

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new EquipmentNotFoundException("존재하지 않는 설비입니다."));
    }

    @Transactional
    public Equipment update(Long id, String code, String name) {
        Equipment equipment = findById(id);

        equipment.update(code, name);

        return equipment;
    }

    public void delete(Long id) {
        Equipment equipment = findById(id);

        equipmentRepository.delete(equipment);
    }
}
