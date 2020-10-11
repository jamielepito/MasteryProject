package learn.mastery.data;

import learn.mastery.domain.Result;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository {

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble(){

        Reservation reservation = new Reservation();

        // 1,2020-10-13,2020-10-17,643,720
        reservation.setResId(1);
        reservation.setStartDate(LocalDate.of(2020,10,13));
        reservation.setEndDate(LocalDate.of(2020,10,17));
        reservation.setGuestId(643);
        reservation.setTotal(BigDecimal.valueOf(720));
        reservation.setHostId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");

        reservations.add(reservation);
    }

    public List<Reservation> findReservationByHost(String hostId){
        return reservations.stream()
                .filter(r -> r.getHostId().equalsIgnoreCase(hostId))
                .collect(Collectors.toList());
    }


    public Reservation addReservation(Reservation reservation){
        return reservation;
    }

    //editReservation(reservation, startDate, endDate);
    public boolean editReservation(Reservation reservation, LocalDate startDate, LocalDate endDate){

        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);

        return true;

    }

}
