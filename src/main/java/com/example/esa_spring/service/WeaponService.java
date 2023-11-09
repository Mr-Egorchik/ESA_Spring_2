package com.example.esa_spring.service;

import com.example.esa_spring.dto.WeaponDTO;
import com.example.esa_spring.entity.Weapon;
import com.example.esa_spring.repositories.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final ModelMapper mapper;

    @Transactional
    public void save(Weapon weapon) {
        weaponRepository.save(weapon);
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
        weaponRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        weaponRepository.deleteAll();
    }

}
