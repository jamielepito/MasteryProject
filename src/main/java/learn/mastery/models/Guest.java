package learn.mastery.models;

import org.springframework.stereotype.Component;

@Component
public class Guest {

    //Guest ID (int)
    //First Name (String)
    //Last Name (String)
    //Email (String)
    //State (String)

    private int guestId;
    private String firstName;
    private String lastName;
    private String email;

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;
}
