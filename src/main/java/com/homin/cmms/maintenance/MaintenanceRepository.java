package com.homin.cmms.maintenance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    Page<Maintenance> findByEquipmentId(Long equipmentId, Pageable pageable);

    Page<Maintenance> findByEquipmentIdAndStatus(Long equipmentId, MaintenanceStatus status, Pageable pageable);

    Optional<Maintenance> findByEquipmentIdAndId(Long equipmentId, Long id);
}
