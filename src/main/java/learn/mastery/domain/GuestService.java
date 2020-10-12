package learn.mastery.domain;

import learn.mastery.data.GuestRepository;
import org.springframework.stereotype.Component;

@Component
public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }
}
