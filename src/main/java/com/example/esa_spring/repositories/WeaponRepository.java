package com.example.esa_spring.repositories;

import com.example.esa_spring.entity.Weapon;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WeaponRepository extends CrudRepository<Weapon, UUID> {
}
