package learn.mastery.domain;

import learn.mastery.data.DataAccessException;
import learn.mastery.data.HostRepository;
import org.springframework.stereotype.Component;

@Component
public class HostService {
    //Implement rules for host

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public String hostLocation(String hostEmail) throws DataAccessException {
        String result = String.format("%s, %s",
                repository.getHostByEmail(hostEmail).getCity(),
                repository.getHostByEmail(hostEmail).getState());
        return result;
    }
}
