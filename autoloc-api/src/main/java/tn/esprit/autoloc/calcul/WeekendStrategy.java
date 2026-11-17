package tn.esprit.autoloc.calcul;

import org.springframework.stereotype.Component;

@Component("WEEKEND")
public class WeekendStrategy implements OffreStrategy {
    @Override
    public double calculer(double tarifJournalier, int jours) {
        return tarifJournalier * 0.80 * jours;
    }
}