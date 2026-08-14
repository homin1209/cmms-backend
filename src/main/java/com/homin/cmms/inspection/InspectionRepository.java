package com.homin.cmms.inspection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    List<Inspection> findByEquipmentId(Long equipmentId);

    Optional<Inspection> findByEquipmentIdAndId(Long equipmentId, Long id);
}
