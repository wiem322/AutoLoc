package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

import java.math.BigDecimal;

@Service
public class ContratServiceImpl extends GenericServiceImpl<Contrat, Long> implements IContratService {

    public ContratServiceImpl(IContratRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Contrat"; }

    @Override
    protected Long getId(Contrat a) { return a.getIdContrat(); }

    @Override
    protected void setFields(Contrat existing, Contrat source) {
        existing.setDateSignature(source.getDateSignature());
        existing.setMontantTotal(source.getMontantTotal());
        existing.setValide(source.isValide());
    }

    @Override
    public boolean check(Contrat a, boolean isNew) {
        return a != null
                && a.getDateSignature() != null
                && a.getMontantTotal() != null
                && a.getMontantTotal().compareTo(BigDecimal.ZERO) >= 0;
    }
}