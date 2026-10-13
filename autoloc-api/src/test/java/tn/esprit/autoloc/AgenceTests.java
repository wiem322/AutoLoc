package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import static org.junit.jupiter.api.Assertions.fail;
import java.math.BigDecimal;
import java.util.ArrayList;
@SpringBootTest
class AgenceTests {

    @Autowired
    private AgenceRepositoryMock agenceRepository;

    @Test
    void addAgence() {
        // Étape 7 : l'agence
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setAdresse("1 Rue Hedi");
        agence.setVille("Tunis");
        agence.setTelephone("71585874");

        // Étape 7 : les deux véhicules
        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("785414TU96");
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setTarifJournalier(new BigDecimal("100"));
        v1.setStatut(StatutVehicule.EN_MAINTENANCE);

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("785414TU95");
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setCategorie(CategorieVehicule.UTILITAIRE);
        v2.setTarifJournalier(new BigDecimal("80"));
        v2.setStatut(StatutVehicule.DISPONIBLE);

        // Côté propriétaire de la relation : remplit la colonne agence_id
        v1.setAgence(agence);
        v2.setAgence(agence);

        // Côté mappedBy : permet la cascade PERSIST
        agence.setVehicules(new ArrayList<>());
        agence.getVehicules().add(v1);
        agence.getVehicules().add(v2);

        // Étape 8 : persister
        agenceRepository.save(agence);
    }
    @Test
    void loadAgence() {
        Iterable<Agence> agences = agenceRepository.findAll();
        StringBuilder sb = new StringBuilder();

        for (Agence agence : agences) {
            sb.append("\n").append(agence.getIdAgence()).append(" | ").append(agence.getNom())
                    .append("\nVehicules Count : ").append(agence.getVehicules().size());

            for (Vehicule v : agence.getVehicules()) {
                sb.append("\n=== ").append(v.getIdVehicule()).append("|").append(v.getImmatriculation());
            }
        }

        fail(sb.toString());
    }
}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}