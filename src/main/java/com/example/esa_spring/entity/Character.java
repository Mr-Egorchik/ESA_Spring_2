package com.example.esa_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="character")
public class Character extends BaseEntity {

    @Column
    private String name;

    @Column
    private int hp;

    @Column
    private int attack;

    @Column
    private int defence;

    @Column
    private int experience;

    @OneToMany(mappedBy = "owner")
    private List<Artefact> artefacts;

    @OneToOne
    @JoinColumn(name="weapon_id")
    private Weapon weapon;

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", hp=" + hp +
                ", attack=" + attack +
                ", defence=" + defence +
                ", experience=" + experience +
                ", weapon=" + weapon +
                '}';
    }
}
