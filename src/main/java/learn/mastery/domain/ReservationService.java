package learn.mastery.domain;

import learn.mastery.data.DataAccessException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.HostLocation;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReservationService {

    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(GuestRepository guestRepository, HostRepository hostRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findReservations(String hostEmail) throws DataAccessException {

        HostLocation host = hostRepository.getHostByEmail(hostEmail);
        List<Reservation> result = reservationRepository.findReservationByHost(host.getHostId());
        return result;
    }

    public Reservation makeReservation(LocalDate startDate, LocalDate endDate, String guestEmail, String hostEmail) throws DataAccessException {
        Reservation reservation = new Reservation();
        reservation.setResId(getNextId(hostEmail));
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setGuestId(guestRepository.findGuestByEmail(guestEmail).getGuestId());
        HostLocation host = hostRepository.getHostByEmail(hostEmail);
        reservation.setHostId(host.getHostId());
        reservation.setTotal(getTotal(host, startDate, endDate));

        //return reservationRepository.addReservation(reservation);
        return reservation;
    }

    private int getNextId(String hostEmail) throws DataAccessException {
        List<Reservation> reservations = findReservations(hostEmail);

        int nextId = 0;
        for (Reservation res : reservations) {
            nextId = Math.max(nextId, res.getResId());
        }
        nextId++;
        return nextId;
    }

    private BigDecimal getTotal(HostLocation host, LocalDate startDate, LocalDate endDate) {

        // TODO: initialize better
        int weekendCount = 0;
        int standardCount = 0;

        // TODO: make this a stream?
        for (LocalDate date = startDate; date.compareTo(endDate) < 0; date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekendCount++;
            } else {
                standardCount++;
            }
        }
        BigDecimal standardTotal = host.getStandardRate().multiply(BigDecimal.valueOf(standardCount));
        BigDecimal weekendTotal = host.getWeekendRate().multiply(BigDecimal.valueOf(weekendCount));

        return standardTotal.add(weekendTotal);
    }


    //  Validate Create
    //Guest, host, and start and end dates are required.
    //The guest and host must already exist in the "database". Guests and hosts cannot be created.
    //The start date must come before the end date.
    //The reservation may never overlap existing reservation dates.
    //The start date must be in the future.

    //  Validate Update
    //Guest, host, and start and end dates are required.
    //The guest and host must already exist in the "database". Guests and hosts cannot be created.
    //The start date must come before the end date.
    //The reservation may never overlap existing reservation dates.

    //  Validate Delete
    //You cannot cancel a reservation that's in the past.
}
