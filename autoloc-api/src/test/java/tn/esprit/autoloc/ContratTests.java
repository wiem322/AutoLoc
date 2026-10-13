package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.ModePaiement;
import tn.esprit.autoloc.domain.Paiement;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class ContratTests {

    @Autowired
    private ContratRepositoryMock contratRepository;

    @Test
    void addContrat() {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("300"));
        contrat.setValide(true);

        Paiement acompte = new Paiement();
        acompte.setMontant(new BigDecimal("100"));
        acompte.setDatePaiement(LocalDate.now());
        acompte.setModePaiement(ModePaiement.CARTE);
        acompte.setContrat(contrat);

        Paiement solde = new Paiement();
        solde.setMontant(new BigDecimal("200"));
        solde.setDatePaiement(LocalDate.now());
        solde.setModePaiement(ModePaiement.ESPECES);
        solde.setContrat(contrat);

        contrat.getPaiements().add(acompte);
        contrat.getPaiements().add(solde);

        contratRepository.save(contrat);
    }

    @Test
    void loadContrat() {
        StringBuilder sb = new StringBuilder();
        for (Contrat c : contratRepository.findAll()) {
            sb.append("\nContrat ").append(c.getIdContrat())
                    .append(" | paiements=").append(c.getPaiements().size());
        }
        fail(sb.toString());
    }
}

interface ContratRepositoryMock extends CrudRepository<Contrat, Long> {
}