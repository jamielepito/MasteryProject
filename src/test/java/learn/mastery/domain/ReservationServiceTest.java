package learn.mastery.domain;

import learn.mastery.data.*;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private ReservationService service;

    @BeforeEach
    void setup(){
        service = new ReservationService(
                new GuestRepositoryDouble(),
                new HostRepositoryDouble(),
                new ReservationRepositoryDouble());
    }


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
        reservation.setHostId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");

        Result<Reservation> result = service.makeReservation(reservation, "y");
        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");
        System.out.println(result.getErrorMessages());

        assertEquals(0, result.getErrorMessages().size());
        assertEquals(2, reservations.size());

    }

    @Test
    void shouldNotMakeReservationEndDateBeforeStartDate() throws DataAccessException{
        Reservation reservation = new Reservation();
        reservation.setEndDate(LocalDate.of(2022, 02, 02));
        reservation.setStartDate(LocalDate.of(2022, 02, 04));
        reservation.setGuestId(802);
        reservation.setTotal(BigDecimal.valueOf(360));
        reservation.setHostId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");

        Result<Reservation> result = service.makeReservation(reservation, "y");
        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");

        assertEquals(1, result.getErrorMessages().size());
        assertEquals(1, reservations.size());
    }

    @Test
    void shouldNotMakeReservationOverlappingDates() throws DataAccessException{
        Reservation reservation = new Reservation();
        reservation.setStartDate(LocalDate.of(2022, 02, 02));
        reservation.setEndDate(LocalDate.of(2022, 02, 04));
        reservation.setGuestId(802);
        reservation.setTotal(BigDecimal.valueOf(360));
        reservation.setHostId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");

        service.makeReservation(reservation, "y");

        Reservation newReservation = new Reservation();
        newReservation.setStartDate(LocalDate.of(2022, 02, 03));
        newReservation.setEndDate(LocalDate.of(2022, 02, 04));
        newReservation.setGuestId(643);
        newReservation.setTotal(BigDecimal.valueOf(360));
        newReservation.setHostId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");

        Result<Reservation> result = service.makeReservation(newReservation, "y");
        System.out.println(result.getErrorMessages());
        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");
       // assertEquals("The reservation may never overlap existing reservation dates.", result.getErrorMessages().get(0));
        assertEquals(2, reservations.size());

    }


    // Reservation reservation, LocalDate startDate, LocalDate endDate, String hostEmail
    @Test
    void shouldEditValidReservation() throws DataAccessException {

        List<Reservation> reservations = service.findReservations("irosenkranc8w@reverbnation.com");

        Reservation reservation = reservations.stream()
                .filter(res -> res.getResId() == 1).collect(Collectors.toList()).stream().findFirst().get();

        Result<Reservation> result = service.editReservation(
                reservation, LocalDate.of(2023, 11, 10), LocalDate.of(2023, 11, 12), "irosenkranc8w@reverbnation.com");

        System.out.println(result.getErrorMessages());
        assertEquals(LocalDate.of(2023, 11, 10), reservation.getStartDate());
        assertEquals(0, result.getErrorMessages().size());

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