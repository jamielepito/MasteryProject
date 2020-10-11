package learn.mastery.domain;

import learn.mastery.data.*;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    void shouldAddExistingReservation() throws DataAccessException {
        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");

    }

    // Reservation reservation, LocalDate startDate, LocalDate endDate, String hostEmail
    @Test
    void shouldEditValidReservation() throws DataAccessException {

        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");

        Reservation reservation = reservations.stream()
                .filter(res -> res.getResId() == 1).collect(Collectors.toList()).stream().findFirst().get();

        Result<Reservation> result = service.editReservation(
                reservation, LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 12), "irosenkranc8w@reverbnation.com");

        assertEquals(0, result.getErrorMessages().size());
        assertEquals(LocalDate.of(2022, 10, 10), reservation.getStartDate());

    }

    @Test
    void shouldNotUpdateOverlappingDate(){

    }

    @Test
    void summarizeReservation() {
    }
}