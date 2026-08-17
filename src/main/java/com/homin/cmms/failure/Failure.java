package com.homin.cmms.failure;

import com.homin.cmms.equipment.Equipment;
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

    protected Failure(Equipment equipment, LocalDateTime occurredAt, String description, FailureStatus status) {
        this.equipment = equipment;
        this.occurredAt = occurredAt;
        this.description = description;
        this.status = status;
    }

    public void update(
            LocalDateTime occurredAt,
            String description,
            FailureStatus status
    ) {
        this.occurredAt = occurredAt;
        this.description = description;
        this.status = status;
    }
}
