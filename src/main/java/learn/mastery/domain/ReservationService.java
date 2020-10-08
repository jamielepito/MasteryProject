package learn.mastery.domain;

import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Reservation;

import java.util.List;

public class ReservationService {

    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(GuestRepository guestRepository, HostRepository hostRepository, ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findReservations(String hostEmail){

        String hostIdentifier = hostRepository.getHostIdentifierByEmail(hostEmail);
        return reservationRepository.findReservationByHost(hostIdentifier);
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
