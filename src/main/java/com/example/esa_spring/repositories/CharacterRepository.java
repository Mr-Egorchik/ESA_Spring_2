package com.example.esa_spring.repositories;

import com.example.esa_spring.entity.Character;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CharacterRepository extends CrudRepository<Character, UUID> {
}
