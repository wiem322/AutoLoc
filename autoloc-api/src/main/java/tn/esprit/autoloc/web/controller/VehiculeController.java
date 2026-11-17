package tn.esprit.autoloc.web.controller;

import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@RestController
@RequestMapping("/vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    public VehiculeController(IVehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping("/{id}")
    public Vehicule findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @PutMapping("/{id}/desaffecter")
    public void desaffecter(@PathVariable Long id) {
        vehiculeService.desaffecter(id);
    }

    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public void affecterAgence(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        vehiculeService.affecterAgence(idVehicule, idAgence);
    }

    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public void affecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}