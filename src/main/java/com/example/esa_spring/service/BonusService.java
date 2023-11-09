package com.example.esa_spring.service;

import com.example.esa_spring.dto.BonusDTO;
import com.example.esa_spring.entity.Bonus;
import com.example.esa_spring.repositories.BonusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BonusService {

    private final BonusRepository bonusRepository;
    private final ModelMapper mapper;

    @Transactional
    public void save(Bonus bonus) {
        bonusRepository.save(bonus);
    }

    @Transactional
    public BonusDTO findById(UUID id) {
        Bonus bonus = bonusRepository.findById(id).orElse(null);
        return bonus == null ? null : mapper.map(bonus, BonusDTO.class);
    }

    @Transactional
    public List<BonusDTO> findAll() {
        return mapper.map(bonusRepository.findAll(), new TypeToken<List<BonusDTO>>(){}.getType());
    }

    @Transactional
    public void delete(UUID id) {
        bonusRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        bonusRepository.deleteAll();
    }

}
