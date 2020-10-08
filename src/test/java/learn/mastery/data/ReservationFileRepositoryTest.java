package learn.mastery.data;

import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {
    static final String SEED_FILE_PATH = "./data/reservations-seed-2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv";
    static final String TEST_FILE_PATH = "./data/reservations_test/2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c.csv";
    static final String TEST_DIR_PATH = "./data/reservations_test";

    ReservationFileRepository repository =new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindReservationByHost() {
        List<Reservation> reservations = repository.findReservationByHost("2e25f6f7-3ef0-4f38-8a1a-2b5eea81409c");
        //assertEquals();
    }

    @Test
    void makeReservation() {
    }

    @Test
    void editReservation() {
    }
}