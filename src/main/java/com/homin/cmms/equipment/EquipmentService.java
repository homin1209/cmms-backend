package com.homin.cmms.equipment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
            throw new IllegalArgumentException("이미 존재하는 설비 코드입니다.");
        }

        Equipment equipment = new Equipment(code, name, status);

        return equipmentRepository.save(equipment);
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Equipment findById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("존재하지 않는 설비입니다."));
    }

    @Transactional
    public Equipment update(Long id, String code, String name, EquipmentStatus status) {
        Equipment equipment = findById(id);

        equipment.update(code, name, status);

        return equipment;
    }

    public void delete(Long id) {
        Equipment equipment = findById(id);

        equipmentRepository.delete(equipment);
    }
}
