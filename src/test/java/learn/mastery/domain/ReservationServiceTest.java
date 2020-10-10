package learn.mastery.domain;

import learn.mastery.data.*;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(
            new GuestRepositoryDouble(),
            new HostRepositoryDouble(),
            new ReservationRepositoryDouble());

    @Test
    void shouldFindExistingHostByEmail() throws DataAccessException {
        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");
        assertEquals(1, reservations.size());
    }

    @Test
    void addReservation() {
    }

    @Test
    void editReservation() {
    }

    @Test
    void summarizeReservation() {
    }
}