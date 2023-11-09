package com.example.esa_spring.repositories;

import com.example.esa_spring.entity.Artefact;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ArtefactRepository extends CrudRepository<Artefact, UUID> {
}
