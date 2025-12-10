
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BookingService {
    public BigDecimal calculateTotal(Movie movie, int tickets) throws BookingException {
        if (movie == null) throw new BookingException("Select a movie first.");
        if (tickets <= 0) throw new BookingException("Ticket count must be at least 1.");
        return movie.getPrice()
                    .multiply(new BigDecimal(tickets))
                    .setScale(2, RoundingMode.HALF_UP);
    }
}
