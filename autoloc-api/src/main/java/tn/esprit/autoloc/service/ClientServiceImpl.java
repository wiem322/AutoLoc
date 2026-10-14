package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
public class ClientServiceImpl extends GenericServiceImpl<Client, Long> implements IClientService {

    public ClientServiceImpl(IClientRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Client"; }

    @Override
    protected Long getId(Client a) { return a.getIdClient(); }

    @Override
    protected void setFields(Client existing, Client source) {
        existing.setNom(source.getNom());
        existing.setPrenom(source.getPrenom());
        existing.setEmail(source.getEmail());
        existing.setTelephone(source.getTelephone());
        existing.setNumPermis(source.getNumPermis());
        existing.setDateInscription(source.getDateInscription());
    }

    @Override
    public boolean check(Client a, boolean isNew) {
        return a != null
                && Utils.isNotEmpty(a.getNom())
                && Utils.isNotEmpty(a.getPrenom())
                && Utils.isNotEmpty(a.getEmail())
                && Utils.isNotEmpty(a.getNumPermis())
                && a.getDateInscription() != null;
    }
}