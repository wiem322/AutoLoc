package tn.esprit.autoloc.web.controller;

import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IEquipementService;

@RestController
@RequestMapping("/equipements")
public class EquipementController {

    private final IEquipementService equipementService;

    public EquipementController(IEquipementService equipementService) {
        this.equipementService = equipementService;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}