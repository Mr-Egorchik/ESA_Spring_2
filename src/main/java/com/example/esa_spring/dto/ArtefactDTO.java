package com.example.esa_spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtefactDTO implements Serializable {

    private UUID id;
    private String name;
    private BonusDTO bonus;

}
