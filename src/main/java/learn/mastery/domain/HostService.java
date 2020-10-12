package learn.mastery.domain;

import learn.mastery.data.DataAccessException;
import learn.mastery.data.HostRepository;
import learn.mastery.models.HostLocation;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

@Component
public class HostService {
    //Implement rules for host

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public String hostLocation(String hostEmail) throws DataAccessException {

        Result<HostLocation> result = validateEmail(hostEmail);
        if (result.isSuccess()){
            return String.format("%s, %s",
                    repository.getHostByEmail(hostEmail).getCity(),
                    repository.getHostByEmail(hostEmail).getState());
        }
        return result.getErrorMessages().toString();
    }

    private Result<HostLocation> validateEmail(String hostEmail) throws DataAccessException {
        HostLocation host = repository.findAll().stream()
                .filter(h -> h.getEmail().equalsIgnoreCase(hostEmail)).findFirst().get();

        Result<HostLocation> result = new Result<HostLocation>(host);

        boolean hostExists = repository.findAll().stream()
                .anyMatch(h -> h.getEmail().equalsIgnoreCase(hostEmail));

        if(!hostExists){
            result.addErrorMessage("Host must exist in file.");
        }
        return result;

    }
}
