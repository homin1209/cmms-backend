package com.homin.cmms.inspection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    Page<Inspection> findByEquipmentId(Long equipmentId, Pageable pageable);

    Page<Inspection> findByEquipmentIdAndResult(Long equipmentId, InspectionResult result, Pageable pageable);

    Optional<Inspection> findByEquipmentIdAndId(Long equipmentId, Long id);
}
