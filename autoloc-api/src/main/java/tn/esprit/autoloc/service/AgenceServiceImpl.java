package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;
import org.springframework.context.annotation.Primary;

@Primary
@Service
public class AgenceServiceImpl extends GenericServiceImpl<Agence, Long> implements IAgenceService {

    public AgenceServiceImpl(IAgenceRepository repository) {
        super(repository);
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
}