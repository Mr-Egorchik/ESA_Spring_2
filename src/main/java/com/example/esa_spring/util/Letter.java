package com.example.esa_spring.util;

import com.example.esa_spring.entity.Change;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Letter {

    private Change change;
    private String notify;
}
