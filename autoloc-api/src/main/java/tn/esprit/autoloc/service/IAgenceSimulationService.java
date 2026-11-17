package tn.esprit.autoloc.service;

import tn.esprit.autoloc.calcul.VehiculeOffre;

public interface IAgenceSimulationService {
    String simuler(Long idAgence, int jours, VehiculeOffre offre);
}