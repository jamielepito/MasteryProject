package learn.mastery.ui;

import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

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

    public void run(){
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

    private void viewReservation(){
        view.printHeader(MainMenuOption.VIEW_RESERVATION.getMessage());
        String email = view.getHostEmail();
        List<Reservation> reservations = reservationService.findReservations(email);
        view.viewReservations(reservations);

    }

    private void makeReservation(){
        view.printHeader(MainMenuOption.MAKE_RESERVATION.getMessage());
    }

    private void editReservation(){
        view.printHeader("Edit a Reservation");
    }

    private void cancelReservation(){
        view.printHeader("Cancel a Reservation");
    }
}
