
package tn.esprit.autoloc.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.service.IAgenceSimulationService;
import tn.esprit.autoloc.service.IAgenceEmployeService;
@Tag(name = "Agences", description = "Gestion des agences de location")
@RestController
@RequestMapping("/agences")
public class AgenceController {

    private final IAgenceService agenceService;
    private final IAgenceSimulationService simulationService;
    private final IAgenceEmployeService employeService;

    public AgenceController(IAgenceService agenceService,
                            IAgenceSimulationService simulationService,
                            IAgenceEmployeService employeService) {
        this.agenceService = agenceService;
        this.simulationService = simulationService;
        this.employeService = employeService;
    }

    @Operation(
            summary = "Consulter toutes les agences",
            description = "Récupère la liste de toutes les agences."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des agences récupérée avec succès")
    })
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    @Operation(
            summary = "Consulter une agence",
            description = "Récupère une agence à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence trouvée"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @GetMapping("/{id}")
    public Agence findById(
            @Parameter(description = "Identifiant de l'agence")
            @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @Operation(
            summary = "Ajouter une agence",
            description = "Crée une nouvelle agence."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agence créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @Operation(
            summary = "Modifier une agence",
            description = "Modifie les informations d'une agence existante."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence modifiée avec succès"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @PutMapping("/{id}")
    public Agence update(
            @Parameter(description = "Identifiant de l'agence")
            @PathVariable Long id,
            @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @Operation(
            summary = "Supprimer une agence",
            description = "Supprime une agence à partir de son identifiant."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Agence supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Agence introuvable")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Identifiant de l'agence")
            @PathVariable Long id) {
        agenceService.deleteById(id);
    }
    @GetMapping("/{id}/simuler")
    public String simuler(@PathVariable Long id,
                          @RequestParam int jours,
                          @RequestParam VehiculeOffre offre) {
        return simulationService.simuler(id, jours, offre);
    }
    @PutMapping("/{id}/employes")
    public Agence ajouterEmployes(@PathVariable Long id, @RequestBody Agence agence) {
        return employeService.mettreAJourAvecEmployes(id, agence);
    }
}
