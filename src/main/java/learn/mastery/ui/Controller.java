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

    private void makeReservation() throws DataAccessException {
        view.printHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
        String guestEmail = getGuestEmail();
        String hostEmail = getHostEmail();
        view.printHeader(hostService.hostLocation(hostEmail));
        List<Reservation> reservations = reservationService.findReservations(hostEmail);
        view.viewReservations(reservations);

        LocalDate startDate = view.readDate("Start Date: ");
        LocalDate endDate = view.readDate("End Date: ");
        Reservation reservation = reservationService.summarizeReservation(startDate, endDate, guestEmail, hostEmail);
        String correct = view.summary(startDate, endDate, reservation.getTotal());
        reservationService.makeReservation(reservation,correct);
    }

    private void editReservation() throws DataAccessException {
        view.printHeader(MainMenuOption.EDIT_RESERVATION.getMessage());
        String hostEmail = getHostEmail();
        view.printHeader(hostService.hostLocation(hostEmail));


        List<Reservation> reservations = reservationService.findReservations(hostEmail);
        Reservation reservation = view.readReservationChoice(reservations);
        LocalDate startDate = view.readDate("Change from " + reservation.getStartDate() + ": ");
        LocalDate endDate = view.readDate("Change from " + reservation.getEndDate() + ": ");
        view.displayResult(reservationService.editReservation(reservation, startDate, endDate, hostEmail));


    }

    private void cancelReservation() throws DataAccessException {
        view.printHeader(MainMenuOption.CANCEL_RESERVATION.getMessage());
        String hostEmail = getHostEmail();
        view.printHeader(hostService.hostLocation(hostEmail));
        List<Reservation> reservations = reservationService.findReservations(hostEmail);
        view.viewReservations(reservations);
        Reservation reservation = view.readReservationChoice(reservations);
        view.displayResult(reservationService.cancelReservation(reservation));

    }

    private String getGuestEmail() throws DataAccessException {
        String guestEmail = null;
        boolean guestExist = false;
        do{
            guestEmail = view.getEmail("Guest");
            guestExist = reservationService.validateGuestEmail(guestEmail);
        }while(!guestExist);
        return guestEmail;
    }

    private String getHostEmail() throws DataAccessException{
        String hostEmail = null;
        boolean hostExist = false;
        do{
            hostEmail = view.getEmail("Host");
            hostExist = reservationService.validateHostEmail(hostEmail);
        }while(!hostExist);

        return hostEmail;
    }
}
