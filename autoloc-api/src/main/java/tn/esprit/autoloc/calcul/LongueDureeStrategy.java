package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("LONGUE_DUREE")
public class LongueDureeStrategy implements OffreStrategy {
    @Override
    public double calculer(double tarifJournalier, int jours) {
        double total = tarifJournalier * 0.75 * jours;
        return total > 2000 ? total * 0.95 : total;
    }
}