package com.homin.cmms.inspection;

import com.homin.cmms.equipment.Equipment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inspectedAt;

    @Enumerated(EnumType.STRING)
    private InspectionResult result;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    public Inspection(Equipment equipment, LocalDateTime inspectedAt, InspectionResult result, String content) {
        this.equipment = equipment;
        this.inspectedAt = inspectedAt;
        this.result = result;
        this.content = content;
    }
}
