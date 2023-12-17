package com.example.esa_spring.repositories;

import com.example.esa_spring.entity.Change;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ChangeRepository extends CrudRepository<Change, UUID> {
}
