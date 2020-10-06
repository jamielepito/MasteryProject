package learn.mastery.domain;

import learn.mastery.data.HostRepository;
import org.springframework.stereotype.Component;

@Component
public class HostService {
    //Implement rules for host

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }
}
