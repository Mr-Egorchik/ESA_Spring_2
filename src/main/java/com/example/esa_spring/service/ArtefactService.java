package com.example.esa_spring.service;

import com.example.esa_spring.dto.ArtefactDTO;
import com.example.esa_spring.entity.Artefact;
import com.example.esa_spring.repositories.ArtefactRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ArtefactService {

    private final ArtefactRepository artefactRepository;
    private final ModelMapper mapper;

    @Transactional
    public void save(Artefact artefact) {
        artefactRepository.save(artefact);
    }

    @Transactional
    public ArtefactDTO findById(UUID id) {
        Artefact artefact = artefactRepository.findById(id).orElse(null);
        return artefact == null ? null : mapper.map(artefact, ArtefactDTO.class);
    }

    @Transactional
    public List<ArtefactDTO> findAll() {
        return mapper.map(artefactRepository.findAll(), new TypeToken<List<ArtefactDTO>>(){}.getType());
    }

    @Transactional
    public void delete(UUID id) {
        artefactRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        artefactRepository.deleteAll();
    }

}
