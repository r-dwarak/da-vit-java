import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class BookingApp extends Application {

    private final NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    private final BookingService service = new BookingService();

    @Override
    public void start(Stage stage) {
        // --- Controls
        ComboBox<Movie> movieBox = new ComboBox<>();
        movieBox.getItems().addAll(
                new Movie("The Grand Heist", new BigDecimal("220.00")),
                new Movie("Starlight Saga", new BigDecimal("180.00")),
                new Movie("Mystery at Dawn", new BigDecimal("150.00"))
        );
        movieBox.setPromptText("Select movie");

        Spinner<Integer> ticketSpinner = new Spinner<>();
        ticketSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        ticketSpinner.setEditable(false);

        Label totalLabel = new Label(currency.format(0));
        totalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button calcBtn = new Button("Calculate Total");
        Button bookBtn = new Button("Book Tickets");
        TextArea messages = new TextArea();
        messages.setEditable(false);
        messages.setPrefRowCount(4);
        messages.setWrapText(true);

        // --- Layout
        GridPane grid = new GridPane();
        grid.setVgap(8); grid.setHgap(10); grid.setPadding(new Insets(12));
        int r = 0;
        grid.add(new Label("Movie:"), 0, r); grid.add(movieBox, 1, r++);
        grid.add(new Label("Tickets:"), 0, r); grid.add(ticketSpinner, 1, r++);
        grid.add(new Label("Total Payable:"), 0, r); grid.add(totalLabel, 1, r++);
        HBox buttons = new HBox(10, calcBtn, bookBtn);
        grid.add(buttons, 1, r++);
        VBox root = new VBox(10, grid, new Label("Messages:"), messages);
        root.setPadding(new Insets(10));

        // --- Handlers
        calcBtn.setOnAction(e -> {
            try {
                var movie = movieBox.getValue();
                int n = ticketSpinner.getValue();
                var total = service.calculateTotal(movie, n);
                totalLabel.setText(currency.format(total));
                append(messages, "Calculated total for \"" + movie.getTitle() + "\" x" + n + " = " + currency.format(total));
            } catch (BookingException be) {
                showError(be.getMessage(), messages);
            } catch (Exception ex) {
                showError("Unexpected error while calculating total.", messages);
            }
        });

        bookBtn.setOnAction(e -> {
            try {
                var movie = movieBox.getValue();
                int n = ticketSpinner.getValue();
                var total = service.calculateTotal(movie, n); // validation + total
                totalLabel.setText(currency.format(total));

                Alert ok = new Alert(Alert.AlertType.INFORMATION);
                ok.setTitle("Booking Confirmed");
                ok.setHeaderText("Booking Successful");
                ok.setContentText("Movie: " + movie.getTitle() +
                        "\nTickets: " + n +
                        "\nTotal: " + currency.format(total));
                ok.showAndWait();

                append(messages, "Booked \"" + movie.getTitle() + "\" x" + n + ". Amount: " + currency.format(total));
            } catch (BookingException be) {
                showError(be.getMessage(), messages);
            } catch (Exception ex) {
                showError("Unexpected error while booking.", messages);
            }
        });

        // --- Scene
        stage.setScene(new Scene(root, 420, 280));
        stage.setTitle("Local Theatre or Movie Ticket Booking");
        stage.show();
    }

    private static void append(TextArea area, String msg) {
        area.appendText(msg + System.lineSeparator());
    }
    private static void showError(String msg, TextArea area) {
        append(area, "! " + msg);
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}
