package com.example.esa_spring.dto;

import com.example.esa_spring.entity.BonusType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "bonus")
public class BonusDTO implements Serializable {

    private UUID id;
    private BonusType bonusType;
    private int bonusValue;

}
