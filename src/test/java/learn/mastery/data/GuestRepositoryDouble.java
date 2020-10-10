package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble(){

        Guest guest = new Guest();
        // 643,Ambrose,Koschek,akoschekhu@mayoclinic.com,(336) 3775870,NC
        guest.setGuestId(643);
        guest.setFirstName("Ambrose");
        guest.setLastName("Koschek");
        guest.setEmail("akoschekhu@mayoclinic.com");
        guest.setEmail("(336) 3775870");
        guest.setState("NC");

        guests.add(guest);
    }

    @Override
    public List<Guest> findAll() {
        return null;
    }

    @Override
    public Guest findGuestByEmail(String email) {
        return null;
    }
}
