package com.homin.cmms.equipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    boolean existsByCode(String code);

    List<Equipment> findByStatus(EquipmentStatus status);

    List<Equipment> findByNameContainingIgnoreCase(String name);

    List<Equipment> findByStatusAndNameContainingIgnoreCase(
            EquipmentStatus status,
            String name
    );
}
