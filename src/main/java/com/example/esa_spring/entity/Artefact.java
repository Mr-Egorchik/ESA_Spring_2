package com.example.esa_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="artefact")
public class Artefact extends BaseEntity {

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="bonus_id")
    private Bonus bonus;

    @ManyToOne
    @JoinColumn(name="character_id")
    private Character owner;

}
