package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;

import static org.junit.jupiter.api.Assertions.fail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class AgenceTests {

    @Autowired
    private AgenceRepositoryMock basicAgenceRepository;   // renommé

    @Autowired
    private IAgenceRepository fullAgenceRepository;       // nouveau

    // ===================== AJOUT =====================

    private void addAgence(CrudRepository<Agence, Long> repository) {
        // Suffixe unique pour éviter les doublons (immatriculation est unique, max 20 caractères)
        int suffixe = Math.abs((int) System.currentTimeMillis());

        // Étape 7 : l'agence
        Agence agence = new Agence();
        agence.setNom("Agence ariana " + suffixe);
        agence.setAdresse("1 Rue Hedi");
        agence.setVille("Tunis");
        agence.setTelephone("71585874");

        // Étape 7 : les deux véhicules
        Vehicule v1 = new Vehicule();
        v1.setImmatriculation("TU96-" + suffixe);
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setCategorie(CategorieVehicule.SUV);
        v1.setTarifJournalier(new BigDecimal("100"));
        v1.setStatut(StatutVehicule.EN_MAINTENANCE);

        Vehicule v2 = new Vehicule();
        v2.setImmatriculation("TU95-" + suffixe);
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
        repository.save(agence);
    }

    @Test
    void basicAddAgence() {
        addAgence(basicAgenceRepository);
    }

    @Test
    void fullAddAgence() {
        addAgence(fullAgenceRepository);
    }

    // ===================== CHARGEMENT =====================

    private void loadAgence(CrudRepository<Agence, Long> repository, String type) {
        Iterable<Agence> agences = repository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("\nDépôt utilisé : ").append(type);

        for (Agence agence : agences) {
            sb.append("\n").append(agence.getIdAgence()).append(" | ").append(agence.getNom())
                    .append("\nVehicules Count : ").append(agence.getVehicules().size());

            for (Vehicule v : agence.getVehicules()) {
                sb.append("\n=== ").append(v.getIdVehicule()).append("|").append(v.getImmatriculation());
            }
        }

        fail(sb.toString());
    }

    @Test
    void basicLoadAgence() {
        loadAgence(basicAgenceRepository, "basic (CrudRepository)");
    }

    @Test
    void fullLoadAgence() {
        loadAgence(fullAgenceRepository, "full (JpaRepository)");
    }

    // ===================== TRI =====================

    @Test
    void loadSortedAgences() {
        List<Agence> agences = fullAgenceRepository.findAll(Sort.by("idAgence").descending());

        StringBuilder sb = new StringBuilder();
        for (Agence a : agences) {
            sb.append("\n").append(a.getIdAgence()).append(" | ").append(a.getNom())
                    .append(" | ").append(a.getVille());
        }

        fail(sb.toString());
    }

    // ===================== PAGINATION =====================

    @Test
    void loadPagedAgences() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("idAgence").descending());
        Page<Agence> page = fullAgenceRepository.findAll(pageable);

        StringBuilder sb = new StringBuilder();
        sb.append("\nTotal pages : ").append(page.getTotalPages())
                .append("\nPage en cours : ").append(page.getNumber());

        for (Agence a : page.getContent()) {
            sb.append("\n").append(a.getIdAgence()).append(" | ").append(a.getNom())
                    .append(" | ").append(a.getVille());
        }

        fail(sb.toString());
    }
}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}