package com.homin.cmms.equipment;

import org.springframework.stereotype.Service;

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
}
