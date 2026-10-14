package tn.esprit.autoloc.service;

import org.springframework.stereotype.Service;
import tn.esprit.autoloc.Utils;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
public class MaintenanceServiceImpl extends GenericServiceImpl<Maintenance, Long> implements IMaintenanceService {

    public MaintenanceServiceImpl(IMaintenanceRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() { return "Maintenance"; }

    @Override
    protected Long getId(Maintenance a) { return a.getIdMaintenance(); }

    @Override
    protected void setFields(Maintenance existing, Maintenance source) {
        existing.setDateDebut(source.getDateDebut());
        existing.setDateFin(source.getDateFin());
        existing.setDescription(source.getDescription());
        existing.setVehicule(source.getVehicule());
    }

    @Override
    public boolean check(Maintenance a, boolean isNew) {
        return a != null
                && a.getDateDebut() != null
                && a.getVehicule() != null
                && (a.getDateFin() == null || !a.getDateFin().isBefore(a.getDateDebut()));
    }
}