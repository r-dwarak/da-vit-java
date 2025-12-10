// Thrown when a plane is interrupted while waiting for clearance
public class ClearanceInterruptedException extends AirportException {
    public ClearanceInterruptedException(String planeId, String op, Throwable cause) {
        super("Clearance interrupted for " + planeId + " during " + op, cause);
    }
}
