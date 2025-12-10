// Base checked exception for tower/runway related issues
public class AirportException extends Exception {
    public AirportException(String message) { super(message); }
    public AirportException(String message, Throwable cause) { super(message, cause); }
}
