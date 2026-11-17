package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;

public interface IAgenceEmployeService {
    Agence mettreAJourAvecEmployes(Long idAgence, Agence agenceAvecEmployes);
}