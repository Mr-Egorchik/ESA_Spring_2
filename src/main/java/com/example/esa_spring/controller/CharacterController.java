package com.example.esa_spring.controller;

import com.example.esa_spring.dto.CharacterDTO;
import com.example.esa_spring.entity.Character;
import com.example.esa_spring.service.CharacterService;
import com.example.esa_spring.service.WeaponService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;
    private final WeaponService weaponService;
    private final ModelMapper mapper;

    @GetMapping("/create_character")
    public String newcharacter(Model model) {
        model.addAttribute("character", new CharacterDTO());
        model.addAttribute("weapons", weaponService.findAll());
        return "new_character";
    }

    @PostMapping("/create_character")
    public String save(@ModelAttribute CharacterDTO character, Model model) {
        if (character.getWeapon().getId() == null) {
            character.setWeapon(null);
        } else {
            character.setWeapon(weaponService.findById(character.getWeapon().getId()));
        }
        characterService.save(mapper.map(character, Character.class));
        return "redirect:/characters";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        characterService.delete(id);
        return "redirect:/characters";
    }

    @GetMapping("/update/{id}")
    public String findGroupById(@PathVariable("id") UUID id, Model model) {
        CharacterDTO character = characterService.findById(id);
        model.addAttribute("character", character);
        model.addAttribute("weapons", weaponService.findAll());
        return "upd_character";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute CharacterDTO character, Model model) {
        character.setId(id);
        if (character.getWeapon().getId() == null) {
            character.setWeapon(null);
        } else {
            character.setWeapon(weaponService.findById(character.getWeapon().getId()));
        }
        characterService.save(mapper.map(character, Character.class));
        return "redirect:/characters";
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("characters", characterService.findAll());
        return "all_characters";
    }

}
