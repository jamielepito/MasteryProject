package learn.mastery.data;

import learn.mastery.domain.Result;
import learn.mastery.models.Reservation;

import java.io.*;
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

    private final String directory;

    public ReservationFileRepository(String directory){
        this.directory = directory;
    }

    public List<Reservation> findReservationByHost(String hostIdentifier) throws DataAccessException {
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
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
        return result;
    }

    public Reservation addReservation(Reservation reservation) throws DataAccessException {
        List<Reservation> reservations = findReservationByHost(reservation.getHostId());
        reservations.add(reservation);
        writeAll(reservations, reservation.getHostId());
        return reservation;

        //ID, Guest name, email
    }

    public boolean editReservation(Reservation reservation, LocalDate startDate, LocalDate endDate) throws DataAccessException {
        // Display Header:  "Host: Last Name"
        List<Reservation> reservations = findReservationByHost(reservation.getHostId());
        for (Reservation res : reservations){
            if (res.getResId() == reservation.getResId()){
                res.setStartDate(startDate);
                res.setEndDate(endDate);

                writeAll(reservations, reservation.getHostId());
                return true;
            }
        }
        return false;


    }

    // Helper Methods


    private void writeAll(List<Reservation> reservations, String hostId) throws DataAccessException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println("id,start_date,end_date,guest_id,total");

            for (Reservation res : reservations) {
                writer.println(serialize(res));
            }
        } catch (FileNotFoundException ex) {
            throw new DataAccessException(ex);
        }
    }

    private Reservation deserialize(String[] fields){
        Reservation reservation = new Reservation();
        reservation.setResId(Integer.parseInt(fields[0]));
        reservation.setStartDate(LocalDate.parse(fields[1]));
        reservation.setEndDate(LocalDate.parse(fields[2]));
        reservation.setGuestId(Integer.parseInt(fields[3]));
        reservation.setTotal(BigDecimal.valueOf(Double.parseDouble(fields[4])));

        return reservation;
    }

    private String serialize(Reservation res){
        return String.format("%s,%s,%s,%s,%s%n",
                res.getResId(),
                res.getStartDate(),
                res.getEndDate(),
                res.getGuestId(),
                res.getTotal());
    }

    private String getFilePath(String hostIdentifier) {
        return Paths.get(directory, hostIdentifier + ".csv").toString();
    }


    private void findByHostandGuestEmail(String guestName, String guestEmail){

    }
}
