    package learn.mastery.domain;

    import learn.mastery.data.DataAccessException;
    import learn.mastery.data.GuestRepository;
    import learn.mastery.data.HostRepository;
    import learn.mastery.data.ReservationRepository;
    import learn.mastery.models.Guest;
    import learn.mastery.models.HostLocation;
    import learn.mastery.models.Reservation;

    import java.math.BigDecimal;
    import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.util.List;


    public class ReservationService {

        private final GuestRepository guestRepository;
        private final HostRepository hostRepository;
        private final ReservationRepository reservationRepository;

        public ReservationService(GuestRepository guestRepository, HostRepository hostRepository, ReservationRepository reservationRepository) {
            this.guestRepository = guestRepository;
            this.hostRepository = hostRepository;
            this.reservationRepository = reservationRepository;
        }

        public List<Reservation> findReservations(String hostEmail) throws DataAccessException {

            HostLocation host = hostRepository.getHostByEmail(hostEmail);
            List<Reservation> result = reservationRepository.findReservationByHost(host.getHostId());
            return result;
        }

        public Result<Reservation> makeReservation(Reservation reservation, String correct) throws DataAccessException {
            Result<Reservation> result = validateCreateInputs(reservation, correct);
            if (result.isSuccess()) {
                reservationRepository.makeReservation(reservation);
                return result;
            }
            return result;
        }

        public boolean validateHostEmail(String email) throws DataAccessException {
            for (HostLocation host : hostRepository.findAll()) {
                if (host.getEmail().equalsIgnoreCase(email)) {
                    return true;
                }
            }
            return false;
        }

        public boolean validateGuestEmail(String email) throws DataAccessException {
            for (Guest guest : guestRepository.findAll()) {
                if (guest.getEmail().equalsIgnoreCase(email)) {
                    return true;
                }
            }
            return false;
        }

        public Result<Reservation> editReservation(Reservation reservation, LocalDate startDate, LocalDate endDate, String hostEmail) throws DataAccessException {
            reservation.setHostId(hostRepository.getHostByEmail(hostEmail).getHostId());
            Result<Reservation> result = validateEditInputs(reservation);

            if (result.isSuccess()) {
                reservationRepository.editReservation(reservation, startDate, endDate);
            }
            return result;
        }

        public Result<Reservation> cancelReservation(Reservation reservation) throws DataAccessException {
            Result<Reservation> result = validateDelete(reservation);

            if (result.isSuccess()) {
                reservationRepository.deleteReservation(reservation);
            }
            return result;
        }

        public Reservation summarizeReservation(LocalDate startDate, LocalDate endDate, String guestEmail, String hostEmail) throws DataAccessException {
            Reservation reservation = new Reservation();
            reservation.setResId(getNextId(hostEmail));
            reservation.setStartDate(startDate);
            reservation.setEndDate(endDate);
            reservation.setGuestId(guestRepository.findGuestByEmail(guestEmail).getGuestId());
            HostLocation host = hostRepository.getHostByEmail(hostEmail);
            reservation.setHostId(host.getHostId());
            reservation.setTotal(getTotal(host, startDate, endDate));

            return reservation;
        }

        private int getNextId(String hostEmail) throws DataAccessException {
            List<Reservation> reservations = findReservations(hostEmail);

            int nextId = 0;
            for (Reservation res : reservations) {
                nextId = Math.max(nextId, res.getResId());
            }
            nextId++;
            return nextId;
        }

        //  Validate Create
        private Result<Reservation> validateCreateInputs(Reservation reservation, String correct) throws DataAccessException {
            Result<Reservation> result = new Result<>(reservation);

            //put this in UI
            if (!correct.equalsIgnoreCase("y")) {
                result.addErrorMessage("Summary was not okay");
            }

            validateDates(reservation, result);
            if (!result.isSuccess()) {
                return result;
            }

            //Guest, host, and start and end dates are required.
            if (reservation.getGuestId() == 0 || reservation.getStartDate() == null
                    || reservation.getEndDate() == null) {
                result.addErrorMessage("Guest, host, and start and end dates are required.");
            }

            return result;

        }

        private Result<Reservation> validateEditInputs(Reservation reservation) throws DataAccessException {
            Result<Reservation> result = new Result<>(reservation);

            validateDates(reservation, result);
            if (!result.isSuccess()) {
                return result;
            }

            return result;
        }


        private Result<Reservation> validateDates(Reservation reservation, Result result) throws DataAccessException {

            List<Reservation> allCurrent = reservationRepository.findReservationByHost(reservation.getHostId());
            allCurrent.remove(reservation);
            for (Reservation existing : allCurrent) {
                if (!(reservation.getEndDate().isBefore(existing.getStartDate()) || existing.getEndDate().isBefore(reservation.getStartDate()))) {
                    result.addErrorMessage("The reservation may never overlap existing reservation dates.");
                }
            }

            if (reservation.getStartDate().compareTo(reservation.getEndDate()) > 0) {
                result.addErrorMessage("The start date must come before the end date.");
            }

            if (!reservation.getStartDate().isAfter(LocalDate.now())) {
                result.addErrorMessage("The start date must be in the future.");
            }

            return result;
        }


        private Result<Reservation> validateDelete(Reservation reservation) throws DataAccessException {
            Result<Reservation> result = new Result<>(reservation);
            if (reservation.getStartDate().isBefore(LocalDate.now())) {
                result.addErrorMessage("You cannot cancel a reservation that's in the past.");
            }
            return result;
        }

        private BigDecimal getTotal(HostLocation host, LocalDate startDate, LocalDate endDate) {

            int weekendCount = 0;
            int standardCount = 0;

            for (LocalDate date = startDate; date.compareTo(endDate) < 0; date = date.plusDays(1)) {
                if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    weekendCount++;
                } else {
                    standardCount++;
                }
            }
            BigDecimal standardTotal = host.getStandardRate().multiply(BigDecimal.valueOf(standardCount));
            BigDecimal weekendTotal = host.getWeekendRate().multiply(BigDecimal.valueOf(weekendCount));

            return standardTotal.add(weekendTotal);
        }
    }
