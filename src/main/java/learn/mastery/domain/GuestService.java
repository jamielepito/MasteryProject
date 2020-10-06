package learn.mastery.domain;

import learn.mastery.data.GuestRepository;
import org.springframework.stereotype.Component;

@Component
public class GuestService {
    //implement rules for guest

    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }
}
