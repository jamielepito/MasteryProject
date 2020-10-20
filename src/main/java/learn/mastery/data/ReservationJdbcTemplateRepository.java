package learn.mastery.data;

import learn.mastery.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationJdbcTemplateRepository implements ReservationRepository {

    public final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Reservation> findReservationByHost(String hostEmail) throws DataAccessException {
        return null;
    }

    @Override
    public Reservation makeReservation(Reservation reservation) throws DataAccessException {
        return null;
    }

    @Override
    public boolean editReservation(Reservation reservation, LocalDate startDate, LocalDate endDate) throws DataAccessException {
        return false;
    }

    @Override
    public boolean deleteReservation(Reservation reservation) throws DataAccessException {
        return false;
    }
}
