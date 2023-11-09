package com.example.esa_spring.controller;

import com.example.esa_spring.dto.ArtefactDTO;
import com.example.esa_spring.dto.BonusDTO;
import com.example.esa_spring.entity.Artefact;
import com.example.esa_spring.service.ArtefactService;
import com.example.esa_spring.service.BonusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/artefacts")
public class ArtefactController {

    private final ArtefactService artefactService;
    private final BonusService bonusService;
    private final ModelMapper mapper;

    @GetMapping("/create_artefact")
    public String newArtefact(Model model) {
        model.addAttribute("artefact", new ArtefactDTO());
        model.addAttribute("bonuses", bonusService.findAll());
        return "new_artefact";
    }

    @PostMapping("/create_artefact")
    public String save(@ModelAttribute ArtefactDTO artefact, Model model) {
        artefact.setBonus(bonusService.findById(artefact.getBonus().getId()));
        artefactService.save(mapper.map(artefact, Artefact.class));
        return "redirect:/artefacts";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") UUID id, Model model) {
        artefactService.delete(id);
        return "redirect:/artefacts";
    }

    @GetMapping("/update/{id}")
    public String findGroupById(@PathVariable("id") UUID id, Model model) {
        ArtefactDTO artefact = artefactService.findById(id);
        model.addAttribute("artefact", artefact);
        model.addAttribute("bonuses", bonusService.findAll());
        return "upd_artefact";
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") UUID id, @ModelAttribute ArtefactDTO artefact, Model model) {
        artefact.setId(id);
        artefact.setBonus(bonusService.findById(artefact.getBonus().getId()));
        artefactService.save(mapper.map(artefact, Artefact.class));
        return "redirect:/artefacts";
    }

    @GetMapping("")
    public String getAll(Model model) {
        model.addAttribute("artefacts", artefactService.findAll());
        return "all_artefacts";
    }

}
