package learn.mastery.data;

import learn.mastery.models.HostLocation;

import java.util.List;

public interface HostRepository {
    HostLocation getHostByEmail(String email) throws DataAccessException;
    List<HostLocation> findAll() throws DataAccessException;
}
