package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.math.BigDecimal;

@Service
public class VehiculeServiceImpl extends GenericServiceImpl<Vehicule, Long> implements IVehiculeService {

    public VehiculeServiceImpl(IVehiculeRepository repository) {
        super(repository);
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
}