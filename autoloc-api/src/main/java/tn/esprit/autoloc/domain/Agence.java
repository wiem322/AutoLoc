package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 200)
    private String adresse;

    @Column(nullable = false, length = 50)
    private String ville;

    @Column(length = 20)
    private String telephone;

    // Q11 : nombre d'employés
    @Column(nullable = false)
    private int effectif;

    @JsonIgnore
    @OneToMany(mappedBy = "agence", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Vehicule> vehicules = new ArrayList<>();

    // WRITE_ONLY : accepté dans le JSON reçu (structure imbriquée, Q13), jamais renvoyé
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employe> employes = new ArrayList<>();

    // Q10 : méthodes qui gardent les DEUX côtés de la relation cohérents
    public void addEmploye(Employe employe) {
        employes.add(employe);
        employe.setAgence(this);
    }

    public void removeEmploye(Employe employe) {
        employes.remove(employe);
        employe.setAgence(null);
    }
}