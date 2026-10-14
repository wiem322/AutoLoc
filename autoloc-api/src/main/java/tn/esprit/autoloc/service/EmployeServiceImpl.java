package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
public class EmployeServiceImpl extends GenericServiceImpl<Employe, Long> implements IEmployeService {

    public EmployeServiceImpl(IEmployeRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Employe"; }

    @Override
    protected Long getId(Employe a) { return a.getIdEmploye(); }

    @Override
    protected void setFields(Employe existing, Employe source) {
        existing.setNom(source.getNom());
        existing.setPrenom(source.getPrenom());
        existing.setRole(source.getRole());
        existing.setAgence(source.getAgence());
    }

    @Override
    public boolean check(Employe a, boolean isNew) {
        return a != null
                && Utils.isNotEmpty(a.getNom())
                && Utils.isNotEmpty(a.getPrenom())
                && a.getRole() != null;
    }
}