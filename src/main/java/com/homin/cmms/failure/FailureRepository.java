package com.homin.cmms.failure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FailureRepository extends JpaRepository<Failure, Long> {

    List<Failure> findByEquipmentId(Long equipmentId);

    Optional<Failure> findByEquipmentIdAndId(Long equipmentId, Long id);
}
