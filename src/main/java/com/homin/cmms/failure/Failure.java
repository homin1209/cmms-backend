package com.homin.cmms.failure;

import com.homin.cmms.common.exception.InvalidFailureStatusTransitionException;
import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.inspection.Inspection;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Failure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    private LocalDateTime occurredAt;

    private String description;

    @Enumerated(EnumType.STRING)
    private FailureStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_id")
    private Inspection inspection;

    protected Failure(Equipment equipment, Inspection inspection, LocalDateTime occurredAt, String description, FailureStatus status) {
        this.equipment = equipment;
        this.inspection = inspection;
        this.occurredAt = occurredAt;
        this.description = description;
        this.status = status;
    }

    public void update(
            Inspection inspection,
            LocalDateTime occurredAt,
            String description,
            FailureStatus status
    ) {
        this.inspection = inspection;
        this.occurredAt = occurredAt;
        this.description = description;
        changeStatus(status);
    }

    public void changeStatus(FailureStatus newStatus) {

        if(status == newStatus) {
            return;
        }

        if(status == FailureStatus.REPORTED && newStatus == FailureStatus.IN_PROGRESS){
            this.status = newStatus;
            return;
        }

        if(status == FailureStatus.IN_PROGRESS && newStatus == FailureStatus.RESOLVED){
            this.status = newStatus;
            return;
        }

        throw new InvalidFailureStatusTransitionException("고장 상태를 " + this.status + "에서 " + newStatus + "로 변경할 수 없습니다.");
    }
}
