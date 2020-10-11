package learn.mastery.ui;

import learn.mastery.data.DataAccessException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Controller {

    // takes in all services and view

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() throws DataAccessException {
        MainMenuOption option;
        view.printHeader("Main Menu");
        do{
            option = view.displayMainMenuAndSelect();
            switch (option){
                case EXIT:
                    System.out.print("Goodbye!");
                    break;
                case VIEW_RESERVATION:
                    viewReservation();
                    break;
                case MAKE_RESERVATION:
                    makeReservation();
                    break;
                case EDIT_RESERVATION:
                    editReservation();
                    break;
                case CANCEL_RESERVATION:
                    cancelReservation();
                    break;
            }
        }while (option != MainMenuOption.EXIT);
    }

    private void viewReservation() throws DataAccessException {
        view.printHeader(MainMenuOption.VIEW_RESERVATION.getMessage());
        String email = view.getEmail("Host");
        List<Reservation> reservations = reservationService.findReservations(email);
        view.viewReservations(reservations);

    }

    // too much work happening here! TODO: refactor
    private void makeReservation() throws DataAccessException {
        view.printHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        String guestEmail = view.getEmail("Guest");
        String hostEmail = view.getEmail("Host");
        view.printHeader(hostService.hostLocation(hostEmail));

        List<Reservation> reservations = reservationService.findReservations(hostEmail);
        view.viewReservations(reservations);

        LocalDate startDate = view.readDate("Start Date: ");
        LocalDate endDate = view.readDate("End Date: ");

        // LocalDate startDate, LocalDate endDate, String guestEmail, String hostEmail
        Reservation reservation = reservationService.summarizeReservation(startDate, endDate, guestEmail, hostEmail);
        String correct = view.summary(startDate, endDate, reservation.getTotal());
        Result result = reservationService.addReservation(reservation,correct);
        view.displayResult(result);
    }

    private void editReservation() throws DataAccessException {
        view.printHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        // TODO: put this in helper method? same as add res
        String guestEmail = view.getEmail("Guest");
        String hostEmail = view.getEmail("Host");
        view.printHeader(hostService.hostLocation(hostEmail));
        // TODO: only return with matching guest email too
        List<Reservation> reservations = reservationService.findReservations(hostEmail);
        Reservation reservation = view.readReservationChoice(reservations);
        LocalDate startDate = view.readDate(reservation.getStartDate() + ": ");
        LocalDate endDate = view.readDate(reservation.getEndDate() + ": ");
        view.displayResult(reservationService.editReservation(reservation, startDate, endDate, hostEmail));


    }

    private void cancelReservation() throws DataAccessException {
        view.printHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        String hostEmail = view.getEmail("Host");
        view.printHeader(hostService.hostLocation(hostEmail));
        List<Reservation> reservations = reservationService.findReservations(hostEmail);
        // put into one method in test
        view.viewReservations(reservations);
        view.readReservationChoice(reservations);

    }
}
