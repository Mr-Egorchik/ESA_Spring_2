package com.example.esa_spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "bonus")
public class Bonus extends BaseEntity {

    @Column
    private BonusType bonusType;

    @Column
    private int bonusValue;

    @OneToMany(mappedBy = "bonus")
    private List<Artefact> artefacts;

}
