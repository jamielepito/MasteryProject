package learn.mastery.data;

import learn.mastery.models.Guest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository {

    //set/get guestId
    //set/get firstName
    //set/get email
    //set/get state
    private final String filePath;
    private final String delimiter = ",";

    public GuestFileRepository(String filePath){
        this.filePath = filePath;
    }

    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            reader.readLine();  // header
            for(String line = reader.readLine(); line != null; line = reader.readLine()){

                String[] fields = line.split(delimiter, -1);
                if (fields.length == 6) {
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

    private Guest deserialize(String[] fields){
        Guest guest = new Guest();
        guest.setGuestId(Integer.parseInt(fields[0]));
        guest.setFirstName(fields[1]);
        guest.setLastName(fields[2]);
        guest.setEmail(fields[3]);
        guest.setPhoneNumber(fields[4]);
        guest.setState(fields[5]);

        return guest;
    }
}
