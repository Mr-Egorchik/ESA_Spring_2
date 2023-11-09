package com.example.esa_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO implements Serializable {

    private UUID id;
    private String name;
    private int hp;
    private int attack;
    private int defence;
    private int experience;
    private List<ArtefactDTO> artefacts;
    private WeaponDTO weapon;

}
