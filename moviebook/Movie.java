
import java.math.BigDecimal;

public class Movie {
    private final String title;
    private final BigDecimal price; // price per ticket

    public Movie(String title, BigDecimal price) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title required");
        if (price == null || price.signum() < 0) throw new IllegalArgumentException("Price must be >= 0");
        this.title = title;
        this.price = price;
    }
    public String getTitle() { return title; }
    public BigDecimal getPrice() { return price; }
    @Override public String toString() { return title + " (" + price + ")"; } // for ComboBox display
}
