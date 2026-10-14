package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.math.BigDecimal;

// Paiement : service en LECTURE SEULE (seuls findById et findAll sont autorisés)
@Service
public class PaiementServiceImpl extends GenericServiceImpl<Paiement, Long> implements IPaiementService {

    private static final String LECTURE_SEULE = "Paiement est en lecture seule";

    public PaiementServiceImpl(IPaiementRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Paiement"; }

    @Override
    protected Long getId(Paiement a) { return a.getIdPaiement(); }

    @Override
    protected void setFields(Paiement existing, Paiement source) {
        throw new UnsupportedOperationException(LECTURE_SEULE);
    }

    @Override
    public boolean check(Paiement a, boolean isNew) {
        return a != null
                && a.getMontant() != null
                && a.getMontant().compareTo(BigDecimal.ZERO) > 0
                && a.getDatePaiement() != null
                && a.getModePaiement() != null
                && a.getContrat() != null;
    }

    @Override
    public Paiement create(Paiement entity) {
        throw new UnsupportedOperationException(LECTURE_SEULE);
    }

    @Override
    public Paiement update(Long id, Paiement entity) {
        throw new UnsupportedOperationException(LECTURE_SEULE);
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException(LECTURE_SEULE);
    }
}