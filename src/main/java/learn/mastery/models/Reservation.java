package learn.mastery.models;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Reservation {
//    Host GUID
//    ID - guest? (int)
//    Start Date (LocalDate)
//    End Date (LocalDate)

    private String hostId;
    private int guestId; // is this id for guest or reservation?
    private LocalDate startDate;
    private LocalDate endDate;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
