package com.homin.cmms.failure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FailureRepository extends JpaRepository<Failure, Long> {

    Page<Failure> findByEquipmentId(Long equipmentId, Pageable pageable);

    Page<Failure> findByEquipmentIdAndStatus(Long equipmentId, FailureStatus status, Pageable pageable);

    Optional<Failure> findByEquipmentIdAndId(Long equipmentId, Long id);
}
