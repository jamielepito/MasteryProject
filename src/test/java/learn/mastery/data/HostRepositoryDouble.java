package learn.mastery.data;

import learn.mastery.models.HostLocation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {
    private final ArrayList<HostLocation> hosts = new ArrayList<>();

    public HostRepositoryDouble(){
        //private String hostId;
        //    private String lastName;
        //    private String email;
        //    private String phone;
        //    private String address;
        //    private String city;
        //    private String state;
        //    private int postalCode;
        //    private BigDecimal standardRate;
        //    private BigDecimal weekendRate;

        // ,,,,,,,,
        HostLocation host = new HostLocation();
        host.setLastName("Rosenkranc");
        host.setEmail("irosenkranc8w@reverbnation.com");
        host.setPhone("(970) 7391162");
        host.setCity("7 Kennedy Plaza");
        host.setState("CO");
        host.setPostalCode(80638);
        host.setStandardRate(BigDecimal.valueOf(180));
        host.setWeekendRate(BigDecimal.valueOf(225));
        host.setHostId("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");

        hosts.add(host);
    }


    @Override
    public HostLocation getHostByEmail(String email) throws DataAccessException {
        return hosts.stream()
                .filter(host -> host.getEmail().equalsIgnoreCase(email))
                .findFirst().get();

    }

    @Override
    public List<HostLocation> findAll() throws DataAccessException {
        return null;
    }
}
