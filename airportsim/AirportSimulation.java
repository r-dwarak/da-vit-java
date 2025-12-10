import java.util.Random;

public class AirportSimulation {
    public static void main(String[] args) {
        int runwayCount = 3;
        int planes = 12;

        // Allow config via CLI (basic validation + exception handling)
        try {
            if (args.length >= 1) runwayCount = Math.max(1, Integer.parseInt(args[0]));
            if (args.length >= 2) planes = Math.max(1, Integer.parseInt(args[1]));
        } catch (NumberFormatException nfe) {
            System.err.println("Invalid CLI args. Usage: java AirportSimulation [runways>=1] [planes>=1]");
            System.err.println("Falling back to defaults: runways=3, planes=12");
            runwayCount = 3; planes = 12;
        }

        RunwayControl tower = new RunwayControl(runwayCount);
        Random rnd = new Random(42); // fixed seed for reproducible runs

        Thread[] threads = new Thread[planes];
        for (int i = 0; i < planes; i++) {
            // Mix of landing vs takeoff; skew slightly toward landing to observe priority
            PlaneType type = (i % 3 == 0 || i % 4 == 0) ? PlaneType.LAND : PlaneType.TAKEOFF;
            threads[i] = new Plane("Plane-" + (i + 1) + "-" + type, type, tower, rnd);
        }

        System.out.println("=== Airport Runway Control Simulation Started ===");
        try {
            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted. Initiating tower shutdown...");
            tower.shutdownTower();
        } catch (RuntimeException re) {
            System.err.println("Unexpected error in main: " + re);
            tower.shutdownTower();
        } finally {
            // Ensure any waiting planes are released with a clear error path
            tower.shutdownTower();
            System.out.println("=== Simulation Finished ===");
        }
    }
}

