package learn.mastery.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import learn.mastery.models.Guest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GuestJdbcTemplateRepository implements GuestRepository {

    public final JdbcTemplate jdbcTemplate;

    private DataSource dataSource = initDataSource();

    private DataSource initDataSource() {
        MysqlDataSource result = new MysqlDataSource();
        result.setUrl("jdbc:mysql://localhost:3306/users");
        result.setUser("root");
        result.setPassword("top-secret-password");
        return result;
    }

    public GuestJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Guest> findAll() {
        final String sql = "select guest_id, first_name, last_name, email, phone, state from guest;";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
           Guest guest = new Guest();
           guest.setGuestId(resultSet.getInt("guest_id"));
           guest.setFirstName(resultSet.getString("first_name"));
           guest.setLastName(resultSet.getString("last_name"));
           guest.setEmail(resultSet.getString("email"));
           guest.setPhoneNumber(resultSet.getString("phone"));
           guest.setState(resultSet.getString("state"));
           return guest;
        });
    }

    @Override
    public Guest findGuestByEmail(String email) {
        return null;
    }
}
