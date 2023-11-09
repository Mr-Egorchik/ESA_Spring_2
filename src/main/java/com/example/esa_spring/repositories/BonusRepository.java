package com.example.esa_spring.repositories;

import com.example.esa_spring.entity.Bonus;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BonusRepository extends CrudRepository<Bonus, UUID> {
}
