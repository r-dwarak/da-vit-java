import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RunwayControl {
    private final ReentrantLock lock = new ReentrantLock(true); // fair to reduce starvation
    private final Condition canLand  = lock.newCondition();
    private final Condition canTakeoff = lock.newCondition();
    private final Deque<Integer> freeRunways = new ArrayDeque<>();
    private volatile boolean shutdown = false;

    private int waitingToLand = 0;
    private int waitingToTakeoff = 0;

    public RunwayControl(int runwayCount) {
        if (runwayCount <= 0) throw new IllegalArgumentException("runwayCount must be > 0");
        for (int i = 1; i <= runwayCount; i++) freeRunways.addLast(i);
    }

    public int requestLanding(String planeId) throws ClearanceInterruptedException, AirportException {
        lock.lock();
        try {
            ensureNotShutdown();
            waitingToLand++;
            try {
                while (freeRunways.isEmpty()) {
                    log(planeId, "waiting to LAND (no runway free)");
                    canLand.await();
                    ensureNotShutdown();
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new ClearanceInterruptedException(planeId, "landing", ie);
            } finally {
                waitingToLand--;
            }
            int runway = freeRunways.removeFirst();
            log(planeId, "CLEARED to LAND on runway " + runway);
            return runway;
        } finally {
            lock.unlock();
        }
    }

    public int requestTakeoff(String planeId) throws ClearanceInterruptedException, AirportException {
        lock.lock();
        try {
            ensureNotShutdown();
            waitingToTakeoff++;
            try {
                // Takeoff must wait if no runway OR any plane is waiting to land
                while (freeRunways.isEmpty() || waitingToLand > 0) {
                    if (freeRunways.isEmpty())
                        log(planeId, "waiting to TAKEOFF (no runway free)");
                    else
                        log(planeId, "waiting to TAKEOFF (landing has priority)");
                    canTakeoff.await();
                    ensureNotShutdown();
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new ClearanceInterruptedException(planeId, "takeoff", ie);
            } finally {
                waitingToTakeoff--;
            }
            int runway = freeRunways.removeFirst();
            log(planeId, "CLEARED for TAKEOFF from runway " + runway);
            return runway;
        } finally {
            lock.unlock();
        }
    }

    public void releaseRunway(int runway) {
        lock.lock();
        try {
            freeRunways.addLast(runway);
            // Strict priority: wake a landing plane first if any exist; otherwise let takeoff proceed
            if (waitingToLand > 0) {
                canLand.signal();
            } else {
                canTakeoff.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void shutdownTower() {
        lock.lock();
        try {
            shutdown = true;
            // Wake everyone so they can see shutdown and exit gracefully
            canLand.signalAll();
            canTakeoff.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private void ensureNotShutdown() throws AirportException {
        if (shutdown) throw new AirportException("Tower is shut down; no new clearances will be granted.");
    }

    static void log(String id, String msg) {
        System.out.printf("[%s] %s: %s%n", LocalTime.now().withNano(0), id, msg);
    }
}
