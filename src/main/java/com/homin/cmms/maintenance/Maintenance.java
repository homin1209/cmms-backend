package com.homin.cmms.maintenance;

import com.homin.cmms.equipment.Equipment;
import com.homin.cmms.failure.Failure;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "failure_id")
    private Failure failure;

    private LocalDateTime performedAt;

    private String description;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    protected Maintenance() {

    }

    public Maintenance(Equipment equipment, Failure failure, LocalDateTime performedAt, String description, MaintenanceStatus status) {
        this.equipment = equipment;
        this.failure = failure;
        this.performedAt = performedAt;
        this.description = description;
        this.status = status;
    }

    public void update(Failure failure, LocalDateTime performedAt, String description, MaintenanceStatus status) {
        this.failure = failure;
        this.performedAt = performedAt;
        this.description = description;
        this.status = status;
    }
}
