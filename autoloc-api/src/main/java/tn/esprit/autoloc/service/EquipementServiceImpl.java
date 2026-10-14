package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
public class EquipementServiceImpl extends GenericServiceImpl<Equipement, Long> implements IEquipementService {

    public EquipementServiceImpl(IEquipementRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Equipement"; }

    @Override
    protected Long getId(Equipement a) { return a.getIdEquipement(); }

    @Override
    protected void setFields(Equipement existing, Equipement source) {
        existing.setLibelle(source.getLibelle());
    }

    @Override
    public boolean check(Equipement a, boolean isNew) {
        return a != null
                && Utils.isNotEmpty(a.getLibelle());
    }
}