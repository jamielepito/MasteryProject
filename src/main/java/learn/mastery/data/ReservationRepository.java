package learn.mastery.data;

import learn.mastery.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findReservationByHost(String hostEmail);
}
