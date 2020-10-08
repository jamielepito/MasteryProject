package learn.mastery.data;

import learn.mastery.models.Reservation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    //Get Reservations for Host
    //Add Reservation
    //Edit Reservation
    //Cancel Reservation
    //set/get id
    //set/get startDate
    //set/get endDate

    private final String directory;

    public ReservationFileRepository(String directory){
        this.directory = directory;
    }

    public List<Reservation> findReservationByHost(String hostIdentifier){
        ArrayList<Reservation> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostIdentifier)))){

            reader.readLine();  // header
            for(String line = reader.readLine(); line != null; line = reader.readLine()){

                String[] fields = line.split(",", -1);
                if (fields.length == 5) {
                    result.add(deserialize(fields));
                }
            }
        } catch (FileNotFoundException e) {
            // do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void makeReservation(String guestEmail, String hostEmail){
        // Display Header:  "Host: Last Name"

        //ID, Guest name, email
    }

    public void editReservation(String guestName, String hostName){
        // Display Header:  "Host: Last Name"

    }

    // Helper Methods



    private Reservation deserialize(String[] fields){
        Reservation reservation = new Reservation();
        reservation.setResId(Integer.parseInt(fields[0]));
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));
        reservation.setGuestId(Integer.parseInt(fields[3]));
        reservation.setTotal(BigDecimal.valueOf(Double.parseDouble(fields[4])));

        return reservation;
    }

    private String getFilePath(String hostIdentifier) {
        return Paths.get(directory, hostIdentifier + ".csv").toString();
    }


    private void findByHostandGuestEmail(String guestName, String guestEmail){

    }
}
