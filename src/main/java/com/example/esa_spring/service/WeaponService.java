package com.example.esa_spring.service;

import com.example.esa_spring.dto.WeaponDTO;
import com.example.esa_spring.entity.Artefact;
import com.example.esa_spring.entity.Change;
import com.example.esa_spring.entity.ChangeType;
import com.example.esa_spring.entity.Weapon;
import com.example.esa_spring.jms.Sender;
import com.example.esa_spring.repositories.WeaponRepository;
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
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final ModelMapper mapper;
    private final Sender sender;

    @Transactional
    public void save(Weapon weapon) {
        UUID id = weapon.getId();
        Weapon prev = id == null ? null : weaponRepository.findById(id).orElse(null);
        Weapon w = weaponRepository.save(weapon);
        Change change = new Change();
        change.setUuid(w.getId());
        change.setTableName("weapon");
        change.setDate(LocalDateTime.now());
        if (prev == null) {
            change.setChangeType(ChangeType.CREATE);
            change.setChanges("Created new entity: " + w);
        }
        else {
            change.setChangeType(ChangeType.UPDATE);
            change.setChanges("Update entity: to "+ w);
        }
        sender.logging(change);
    }

    @Transactional
    public WeaponDTO findById(UUID id) {
        Weapon weapon = weaponRepository.findById(id).orElse(null);
        return weapon == null ? null : mapper.map(weapon, WeaponDTO.class);
    }

    @Transactional
    public List<WeaponDTO> findAll() {
        return mapper.map(weaponRepository.findAll(), new TypeToken<List<WeaponDTO>>(){}.getType());
    }

    @Transactional
    public void delete(UUID id) {
        Change change = new Change();
        change.setUuid(id);
        change.setTableName("weapon");
        change.setDate(LocalDateTime.now());
        change.setChangeType(ChangeType.DELETE);
        change.setChanges("Deleted entity: " + weaponRepository.findById(id));
        sender.logging(change);
        weaponRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        weaponRepository.deleteAll();
    }

}
