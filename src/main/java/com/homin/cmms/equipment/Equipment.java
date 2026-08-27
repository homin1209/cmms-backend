package com.homin.cmms.equipment;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status = EquipmentStatus.RUNNING;

    protected Equipment() {

    }

    public Equipment(String code, String name) {
        this(code, name, EquipmentStatus.RUNNING);
    }

    public Equipment(String code, String name, EquipmentStatus status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public void update(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void changeStatus(EquipmentStatus status) {
        this.status = status;
    }
}
