package com.example.esa_spring.service;

import com.example.esa_spring.dto.ArtefactDTO;
import com.example.esa_spring.entity.Artefact;
import com.example.esa_spring.entity.Bonus;
import com.example.esa_spring.entity.Change;
import com.example.esa_spring.entity.ChangeType;
import com.example.esa_spring.jms.Sender;
import com.example.esa_spring.repositories.ArtefactRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ArtefactService {

    private final ArtefactRepository artefactRepository;
    private final ModelMapper mapper;
    private final Sender sender;

    @Transactional
    public void save(Artefact artefact) {
        UUID id = artefact.getId();
        Artefact prev = id == null ? null : artefactRepository.findById(id).orElse(null);
        Artefact a = artefactRepository.save(artefact);
        Change change = new Change();
        change.setUuid(a.getId());
        change.setTableName("artefact");
        change.setDate(LocalDateTime.now());
        if (prev == null) {
            change.setChangeType(ChangeType.CREATE);
            change.setChanges("Created new entity: " + a);
        }
        else {
            change.setChangeType(ChangeType.UPDATE);
            change.setChanges("Update entity: to "+ a);
        }
        sender.logging(change);
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
        Change change = new Change();
        change.setUuid(id);
        change.setTableName("artefact");
        change.setDate(LocalDateTime.now());
        change.setChangeType(ChangeType.DELETE);
        change.setChanges("Deleted entity: " + artefactRepository.findById(id));
        sender.logging(change);
        artefactRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        artefactRepository.deleteAll();
    }

}
