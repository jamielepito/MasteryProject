package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.HostLocation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HostFileRepository implements HostRepository{

    private final String filePath;
    private final String delimiter = ",";

    public HostFileRepository(String filePath){
        this.filePath = filePath;
    }

    public HostLocation getHostByEmail(String email) throws DataAccessException {
        HostLocation host = findAll().stream().
                filter(h -> h.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .get();

        return host;
    }

    public List<HostLocation> findAll() throws DataAccessException {
        ArrayList<HostLocation> result = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            reader.readLine();  // header
            for(String line = reader.readLine(); line != null; line = reader.readLine()){

                String[] fields = line.split(delimiter, -1);
                if (fields.length == 10) {
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


    private HostLocation deserialize(String[] fields){
        HostLocation host = new HostLocation();
        // id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate
        host.setHostId(fields[0]);
        host.setLastName(fields[1]);
        host.setEmail(fields[2]);
        host.setPhone(fields[3]);
        host.setAddress(fields[4]);
        host.setCity(fields[5]);
        host.setState(fields[6]);
        host.setPostalCode(Integer.parseInt(fields[7]));
        host.setStandardRate(BigDecimal.valueOf(Double.parseDouble(fields[8])));
        host.setWeekendRate(BigDecimal.valueOf(Double.parseDouble(fields[9])));

        return host;
    }
}
