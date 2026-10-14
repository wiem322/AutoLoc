package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
public class ReservationServiceImpl extends GenericServiceImpl<Reservation, Long> implements IReservationService {

    public ReservationServiceImpl(IReservationRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Reservation"; }

    @Override
    protected Long getId(Reservation a) { return a.getIdReservation(); }

    @Override
    protected void setFields(Reservation existing, Reservation source) {
        existing.setDateDebut(source.getDateDebut());
        existing.setDateFin(source.getDateFin());
        existing.setStatut(source.getStatut());
        existing.setClient(source.getClient());
        existing.setVehicule(source.getVehicule());
        existing.setContrat(source.getContrat());
    }

    @Override
    public boolean check(Reservation a, boolean isNew) {
        return a != null
                && a.getDateDebut() != null
                && a.getDateFin() != null
                && !a.getDateFin().isBefore(a.getDateDebut())
                && a.getStatut() != null
                && a.getClient() != null
                && a.getVehicule() != null;
    }
}