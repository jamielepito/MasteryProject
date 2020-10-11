package learn.mastery.data;

import learn.mastery.models.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> findReservationByHost(String hostEmail) throws DataAccessException;

    Reservation addReservation(Reservation reservation) throws DataAccessException;

    boolean editReservation(Reservation reservation, LocalDate startDate, LocalDate endDate) throws DataAccessException;

    boolean deleteReservation(Reservation reservation) throws DataAccessException;
}