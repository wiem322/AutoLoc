package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.service.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ServicesTests {

    // Si un de ces 8 services manque, Spring refuse de démarrer le test
    @Autowired IVehiculeService vehiculeService;
    @Autowired IReservationService reservationService;
    @Autowired IContratService contratService;
    @Autowired IPaiementService paiementService;
    @Autowired IEmployeService employeService;

    @Autowired IClientService clientService;
    @Autowired IMaintenanceService maintenanceService;
    @Autowired IEquipementService equipementService;

    @Test
    void paiementEstEnLectureSeule() {
        assertThrows(UnsupportedOperationException.class, () -> paiementService.create(new Paiement()));
        assertThrows(UnsupportedOperationException.class, () -> paiementService.update(1L, new Paiement()));
        assertThrows(UnsupportedOperationException.class, () -> paiementService.deleteById(1L));
    }

    @Test
    void findByIdIntrouvable() {
        assertThrows(ResourceNotFoundException.class, () -> clientService.findById(-1L));
    }
}