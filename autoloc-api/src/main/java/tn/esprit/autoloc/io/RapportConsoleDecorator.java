package tn.esprit.autoloc.io;

import tn.esprit.autoloc.domain.Agence;

public class RapportConsoleDecorator implements Rapport {

    private final Rapport delegate;

    public RapportConsoleDecorator(Rapport delegate) {
        this.delegate = delegate;
    }

    @Override
    public String generer(Agence agence, double total) {
        System.out.println(RapportTexte.enTete(agence));   // entête sur la console
        return delegate.generer(agence, total);            // le total est retourné
    }
}