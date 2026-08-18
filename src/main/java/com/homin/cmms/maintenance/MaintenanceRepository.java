package com.homin.cmms.maintenance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByEquipmentId(Long equipmentId);

    Optional<Maintenance> findByEquipmentIdAndId(Long equipmentId, Long id);
}
