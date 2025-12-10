import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Plane extends Thread {
    private final String id;
    private final PlaneType type;
    private final RunwayControl tower;
    private final Random rnd;
    private final int arrivalDelayMs;   // time before requesting runway
    private final int runwayUseMs;      // time occupying the runway

    public Plane(String id, PlaneType type, RunwayControl tower, Random rnd) {
        if (id == null || type == null || tower == null || rnd == null)
            throw new IllegalArgumentException("Plane: id/type/tower/rnd must be non-null");
        this.id = id;
        this.type = type;
        this.tower = tower;
        this.rnd = rnd;
        this.arrivalDelayMs = 300 + rnd.nextInt(900);  // 0.3–1.2s
        this.runwayUseMs = 700 + rnd.nextInt(900);     // 0.7–1.6s
        setName(id);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(arrivalDelayMs); // arrive at tower at staggered times
            int runway;
            if (type == PlaneType.LAND) {
                runway = tower.requestLanding(id);
            } else {
                runway = tower.requestTakeoff(id);
            }
            // Occupy runway (exclusive)
            System.out.printf("[%s] %s: USING runway %d (%s)%n",
                    LocalTime.now().withNano(0), id, runway, type);
            TimeUnit.MILLISECONDS.sleep(runwayUseMs);
            System.out.printf("[%s] %s: VACATED runway %d%n",
                    LocalTime.now().withNano(0), id, runway);
            tower.releaseRunway(runway);
        } catch (ClearanceInterruptedException cie) {
            System.err.println("WARN: " + cie.getMessage());
        } catch (AirportException ae) {
            System.err.println("ERROR: " + id + " aborted: " + ae.getMessage());
        } catch (InterruptedException ie) {
            // If the plane thread is interrupted while sleeping/using runway
            Thread.currentThread().interrupt();
            System.err.println("WARN: " + id + " interrupted during operation.");
        } catch (RuntimeException re) {
            // Catch-all for programming errors; keeps simulation running
            System.err.println("FATAL: " + id + " encountered unexpected error: " + re);
        }
    }
}