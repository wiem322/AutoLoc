package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.calcul.OffreStrategy;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    private final Map<String, OffreStrategy> strategies;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    public VehiculeServiceImpl(IVehiculeRepository repository,
                               Map<String, OffreStrategy> strategies,
                               IAgenceRepository agenceRepository,
                               IEquipementRepository equipementRepository) {
        super(repository);
        this.strategies = strategies;
        this.agenceRepository = agenceRepository;
        this.equipementRepository = equipementRepository;
    }

    @Override
    protected String getEntityName() { return "Vehicule"; }

    @Override
    protected Long getId(Vehicule a) { return a.getIdVehicule(); }

    @Override
    protected void setFields(Vehicule existing, Vehicule source) {
        existing.setImmatriculation(source.getImmatriculation());
        existing.setMarque(source.getMarque());
        existing.setModele(source.getModele());
        existing.setCategorie(source.getCategorie());
        existing.setTarifJournalier(source.getTarifJournalier());
        existing.setStatut(source.getStatut());
        existing.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Vehicule a, boolean isNew) {
        return a != null
                && Utils.isNotEmpty(a.getImmatriculation())
                && Utils.isNotEmpty(a.getMarque())
                && Utils.isNotEmpty(a.getModele())
                && a.getCategorie() != null
                && a.getStatut() != null
                && a.getTarifJournalier() != null
                && a.getTarifJournalier().compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public double calculTarif(Vehicule vehicule, int jours, VehiculeOffre offre) {
        return strategies.get(offre.name())
                .calculer(vehicule.getTarifJournalier().doubleValue(), jours);
    }

    @Override
    @Transactional
    public void desaffecter(Long idVehicule) {
        findById(idVehicule).setAgence(null);
    }

    @Override
    @Transactional
    public void affecterAgence(Long idVehicule, Long idAgence) {
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", String.valueOf(idAgence)));
        findById(idVehicule).setAgence(agence);
    }

    @Override
    @Transactional
    public void affecterEquipement(Long idVehicule, Long idEquipement) {
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", String.valueOf(idEquipement)));
        findById(idVehicule).getEquipements().add(equipement);
    }
    @Override
    @Transactional
    public void deleteById(Long id) {
        Vehicule vehicule = findById(id);
        if (vehicule.getAgence() != null) {
            vehicule.getAgence().getVehicules().remove(vehicule);
        }
        super.deleteById(id);
    }
}