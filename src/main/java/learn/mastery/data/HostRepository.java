package learn.mastery.data;

import learn.mastery.models.HostLocation;

public interface HostRepository {
    HostLocation getHostByEmail(String email) throws DataAccessException;
}
