package tn.esprit.autoloc.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.io.RapportFactory;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;

@Primary
@Service
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long>
        implements IAgenceService, IAgenceSimulationService, IAgenceEmployeService {

    private final IVehiculeService vehiculeService;
    private final IEmployeService employeService;

    public AgenceServiceImpl(IAgenceRepository repository,
                             IVehiculeService vehiculeService,
                             IEmployeService employeService) {
        super(repository);
        this.vehiculeService = vehiculeService;
        this.employeService = employeService;
    }

    @Override
    protected String getEntityName() { return "Agence"; }

    @Override
    protected Long getId(Agence a) { return a.getIdAgence(); }

    @Override
    protected void setFields(Agence existing, Agence source) {
        existing.setNom(source.getNom());
        existing.setAdresse(source.getAdresse());
        existing.setVille(source.getVille());
        existing.setTelephone(source.getTelephone());
    }

    @Override
    public boolean check(Agence a, boolean isNew) {
        return a != null
                && Utils.isNotEmpty(a.getNom())
                && Utils.isNotEmpty(a.getAdresse())
                && Utils.isNotEmpty(a.getVille());
    }

    @Override
    public boolean hasVehicules(Long id) {
        return !findById(id).getVehicules().isEmpty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Agence agence = findById(id);

        if (agence.getVehicules() != null) {
            for (Vehicule vehicule : agence.getVehicules()) {
                vehicule.setAgence(null);
            }
        }

        super.deleteById(id);
    }

    @Override
    public String simuler(Long idAgence, int jours, VehiculeOffre offre) {
        Agence a = findById(idAgence);
        double total = a.getVehicules().stream()
                .mapToDouble(v -> vehiculeService.calculTarif(v, jours, offre))
                .sum();

        return RapportFactory.creer().generer(a, total);
    }

    // Q12 : version SANS transaction (volontaire, pour observer le problème des Q15-Q16)
    @Override
    @Transactional
    public Agence mettreAJourAvecEmployes(Long idAgence, Agence source) {
        // 1. mettre à jour d'abord l'agence
        Agence agence = update(idAgence, source);

        // 2. insérer les employés avec le service des employés
        List<Employe> nouveaux = source.getEmployes();
        for (Employe e : nouveaux) {
            agence.addEmploye(e);
            employeService.create(e);
        }

        // 3. mettre à jour l'effectif avec la taille de la liste
        agence.setEffectif(nouveaux.size());
        return repository.save(agence);
    }
}