package com.example.esa_spring.controller;

import com.example.esa_spring.dto.BonusDTO;
import com.example.esa_spring.entity.Bonus;
import com.example.esa_spring.service.BonusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bonuses")
public class BonusController {

    private final BonusService bonusService;
    private final ModelMapper mapper;

    @GetMapping("/create_bonus")
    public String newBonus(Model model) {
        model.addAttribute("bonus", new BonusDTO());
        return "new_bonus";
    }

    @PostMapping("/create_bonus")
    public String save(@ModelAttribute BonusDTO bonus, Model model) {
        bonusService.save(mapper.map(bonus, Bonus.class));
        return "redirect:/bonuses";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        bonusService.delete(id);
        return "redirect:/bonuses";
    }

    @GetMapping("/update/{id}")
    public String findGroupById(@PathVariable("id") UUID id, Model model) {
        BonusDTO bonus = bonusService.findById(id);
        model.addAttribute("bonus", bonus);
        return "upd_bonus";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute BonusDTO bonus, Model model) {
        bonus.setId(id);
        bonusService.save(mapper.map(bonus, Bonus.class));
        return "redirect:/bonuses";
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("bonuses", bonusService.findAll());
        return "all_bonuses";
    }


}
