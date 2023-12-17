package com.example.esa_spring.service;

import com.example.esa_spring.dto.CharacterDTO;
import com.example.esa_spring.entity.Artefact;
import com.example.esa_spring.entity.Change;
import com.example.esa_spring.entity.ChangeType;
import com.example.esa_spring.entity.Character;
import com.example.esa_spring.jms.Sender;
import com.example.esa_spring.repositories.CharacterRepository;
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
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final ModelMapper mapper;
    private final Sender sender;

    @Transactional
    public void save(Character character) {
        UUID id = character.getId();
        Character prev = id == null ? null : characterRepository.findById(id).orElse(null);
        Character c = characterRepository.save(character);
        Change change = new Change();
        change.setUuid(c.getId());
        change.setTableName("character");
        change.setDate(LocalDateTime.now());
        if (prev == null) {
            change.setChangeType(ChangeType.CREATE);
            change.setChanges("Created new entity: " + c);
        }
        else {
            change.setChangeType(ChangeType.UPDATE);
            change.setChanges("Update entity: to "+ c);
        }
        sender.logging(change);
    }

    @Transactional
    public CharacterDTO findById(UUID id) {
        Character character = characterRepository.findById(id).orElse(null);
        return character == null ? null : mapper.map(character, CharacterDTO.class);
    }

    @Transactional
    public List<CharacterDTO> findAll() {
        return mapper.map(characterRepository.findAll(), new TypeToken<List<CharacterDTO>>(){}.getType());
    }

    @Transactional
    public void delete(UUID id) {
        Change change = new Change();
        change.setUuid(id);
        change.setTableName("character");
        change.setDate(LocalDateTime.now());
        change.setChangeType(ChangeType.DELETE);
        change.setChanges("Deleted entity: " + characterRepository.findById(id));
        sender.logging(change);
        characterRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        characterRepository.deleteAll();
    }

}
