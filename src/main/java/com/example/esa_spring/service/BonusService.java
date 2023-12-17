package com.example.esa_spring.service;

import com.example.esa_spring.dto.BonusDTO;
import com.example.esa_spring.entity.Bonus;
import com.example.esa_spring.entity.Change;
import com.example.esa_spring.entity.ChangeType;
import com.example.esa_spring.jms.Sender;
import com.example.esa_spring.repositories.BonusRepository;
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
public class BonusService {

    private final BonusRepository bonusRepository;
    private final ModelMapper mapper;
    private final Sender sender;

    @Transactional
    public void save(Bonus bonus) {
        UUID id = bonus.getId();
        Bonus prev = id == null ? null : bonusRepository.findById(id).orElse(null);
        Bonus b = bonusRepository.save(bonus);
        Change change = new Change();
        change.setUuid(b.getId());
        change.setTableName("bonus");
        change.setDate(LocalDateTime.now());
        if (prev == null) {
            change.setChangeType(ChangeType.CREATE);
            change.setChanges("Created new entity: " + b);
        }
        else {
            change.setChangeType(ChangeType.UPDATE);
            change.setChanges("Update entity: to "+ b);
        }
        sender.logging(change);
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
        Change change = new Change();
        change.setUuid(id);
        change.setTableName("bonus");
        change.setDate(LocalDateTime.now());
        change.setChangeType(ChangeType.DELETE);
        change.setChanges("Deleted entity: " + bonusRepository.findById(id));
        sender.logging(change);
        bonusRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        bonusRepository.deleteAll();
    }

}
