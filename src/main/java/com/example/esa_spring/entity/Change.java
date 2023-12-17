package com.example.esa_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "change")
public class Change extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column
    private ChangeType changeType;

    @Column
    private String tableName;

    @Column
    private UUID uuid;

    @Column
    private String changes;

    @Column
    private LocalDateTime date;

    @Override
    public String toString() {
        return date + " " + changeType + " on table " + tableName + " at object with id " + uuid + ". Changes: " + changes;
    }
}
