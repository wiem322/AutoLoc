package tn.esprit.autoloc.io;

import tn.esprit.autoloc.domain.Agence;

@AfficheConsole
public class RapportTexte implements Rapport {

    @Override
    public String generer(Agence agence, double total) {
        return enTete(agence) + "\nTotal des tarifs : " + total;
    }

    public static String enTete(Agence a) {
        return "Agence " + a.getIdAgence() + " - " + a.getNom() + " - Tel : " + a.getTelephone();
    }
}