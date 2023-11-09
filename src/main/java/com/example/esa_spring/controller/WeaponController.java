package com.example.esa_spring.controller;

import com.example.esa_spring.dto.WeaponDTO;
import com.example.esa_spring.entity.Weapon;
import com.example.esa_spring.service.WeaponService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weapons")
public class WeaponController {

    private final WeaponService weaponService;
    private final ModelMapper mapper;

    @GetMapping("/create_weapon")
    public String newWeapon(Model model) {
        model.addAttribute("weapon", new WeaponDTO());
        return "new_weapon";
    }

    @PostMapping("/create_weapon")
    public String save(@ModelAttribute WeaponDTO weapon, Model model) {
        weaponService.save(mapper.map(weapon, Weapon.class));
        return "redirect:/weapons";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        weaponService.delete(id);
        return "redirect:/weapons";
    }

    @GetMapping("/update/{id}")
    public String findGroupById(@PathVariable("id") UUID id, Model model) {
        WeaponDTO weapon = weaponService.findById(id);
        model.addAttribute("weapon", weapon);
        return "upd_weapon";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute WeaponDTO weapon, Model model) {
        weapon.setId(id);
        weaponService.save(mapper.map(weapon, Weapon.class));
        return "redirect:/weapons";
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("weapons", weaponService.findAll());
        return "all_weapons";
    }

}
