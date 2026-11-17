package tn.esprit.autoloc.io;

public class RapportFactory {

    private RapportFactory() {}

    public static Rapport creer() {
        Rapport rapport = new RapportTexte();
        if (rapport.getClass().isAnnotationPresent(AfficheConsole.class)) {
            rapport = new RapportConsoleDecorator(rapport);
        }
        return rapport;
    }
}