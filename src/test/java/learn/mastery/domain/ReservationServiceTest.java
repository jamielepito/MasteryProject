package learn.mastery.domain;

import learn.mastery.data.*;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
    void shouldMakeReservation() throws DataAccessException {
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2022, 02, 02));
        reservation.setEndDate(LocalDate.of(2022, 02, 04));
        reservation.setGuestId(802);
        reservation.setTotal(BigDecimal.valueOf(360));

        Reservation result = service.makeReservation(reservation, "y");
        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");

        assertEquals(1, reservations.size());
        assertEquals(LocalDate.of(2022, 02, 02), result.getStartDate());

    }

    @Test
    void shouldNotMakeReservationEndDateBeforeStartDate() throws DataAccessException{
        Reservation reservation = new Reservation();
        reservation.setEndDate(LocalDate.of(2022, 02, 02));
        reservation.setStartDate(LocalDate.of(2022, 02, 04));
        reservation.setGuestId(802);
        reservation.setTotal(BigDecimal.valueOf(360));

        Reservation result = service.makeReservation(reservation, "y");
        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");

        assertEquals(0, reservations.size());
    }

    @Test
    void shouldNotMakeReservationOverlappingDates() throws DataAccessException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2022, 02, 02));
        reservation.setEndDate(LocalDate.of(2022, 02, 04));
        reservation.setGuestId(802);
        reservation.setTotal(BigDecimal.valueOf(360));
        service.makeReservation(reservation, "y");

        Reservation newReservation = new Reservation();
        newReservation.setStartDate(LocalDate.of(2022, 02, 01));
        newReservation.setEndDate(LocalDate.of(2022, 02, 03));
        newReservation.setGuestId(643);
        newReservation.setTotal(BigDecimal.valueOf(360));
        service.makeReservation(newReservation, "y");


        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");
        assertEquals(1, reservations.size());
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

    @Test
    void shouldNotDeletePastReservation(){

    }

}