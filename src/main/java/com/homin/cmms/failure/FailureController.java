package com.homin.cmms.failure;

import com.homin.cmms.failure.dto.FailureCreateRequest;
import com.homin.cmms.failure.dto.FailureResponse;
import com.homin.cmms.failure.dto.FailureUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipments/{equipmentId}/failures")
public class FailureController {

    private final FailureService failureService;

    public FailureController(FailureService failureService) {
        this.failureService = failureService;
    }

    @PostMapping
    public ResponseEntity<FailureResponse> create(
            @PathVariable Long equipmentId,
            @Valid @RequestBody FailureCreateRequest request
    ) {
        Failure failure = failureService.create(
                equipmentId,
                request.getInspectionId(),
                request.getOccurredAt(),
                request.getDescription(),
                request.getStatus()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(FailureResponse.from(failure));
    }

    @GetMapping
    public Page<FailureResponse> findByEquipmentId(
            @PathVariable Long equipmentId,
            @RequestParam(required = false) FailureStatus status,
            Pageable pageable
    ) {
        Page<Failure> failures =
                failureService.findByEquipmentId(equipmentId, status, pageable);

        return failures.map(FailureResponse::from);
    }

    @GetMapping("/{id}")
    public FailureResponse findById(
            @PathVariable Long equipmentId,
            @PathVariable Long id
    ) {
        Failure failure = failureService.findById(equipmentId, id);

        return FailureResponse.from(failure);
    }

    @PutMapping("/{id}")
    public FailureResponse update(
            @PathVariable Long equipmentId,
            @PathVariable Long id,
            @Valid @RequestBody FailureUpdateRequest request
    ) {
        Failure failure = failureService.update(equipmentId, id, request.getInspectionId(), request.getOccurredAt(), request.getDescription(), request.getStatus());

        return FailureResponse.from(failure);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long equipmentId,
            @PathVariable Long id
    ) {
        failureService.delete(equipmentId, id);

        return ResponseEntity.noContent().build();
    }
}
