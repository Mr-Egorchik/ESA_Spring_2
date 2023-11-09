package com.example.esa_spring.service;

import com.example.esa_spring.dto.CharacterDTO;
import com.example.esa_spring.entity.Character;
import com.example.esa_spring.repositories.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final ModelMapper mapper;

    @Transactional
    public void save(Character character) {
        characterRepository.save(character);
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
        characterRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        characterRepository.deleteAll();
    }

}
