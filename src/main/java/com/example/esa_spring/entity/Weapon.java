package com.example.esa_spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="weapon")
public class Weapon extends BaseEntity {

    @Column
    private String name;

    @Column
    private int attack;

    @OneToOne(mappedBy = "weapon")
    private Character owner;

    @Override
    public String toString() {
        return "Weapon{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", owner=" + owner +
                '}';
    }
}
