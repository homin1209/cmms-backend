package com.homin.cmms.equipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    boolean existsByCode(String code);

    Page<Equipment> findByStatus(EquipmentStatus status, Pageable pageable);

    Page<Equipment> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Equipment> findByStatusAndNameContainingIgnoreCase(
            EquipmentStatus status,
            String name,
            Pageable pageable
    );
}
