package gr1mly4memes.slime.util.tps;

/**
 * Tracks ticks-per-second and the number of ticks missed so gameplay timing can be compensated
 * while the server is lagging behind.
 *
 * <p>This class sits on a hot path: {@link #getMostAccurateTPS()} is consulted for every block
 * break tick, every accelerated block entity tick, item pickups and portal transfers, so none of
 * the accessors may allocate.
 */
public class TPSCalculator {
    public Long lastTickNanos;
    public Long currentTickNanos;
    private double allMissedTicks = 0;

    /**
     * Fixed-size ring buffer of recent TPS samples.
     *
     * <p>This used to be a {@code CopyOnWriteArrayList<Double>}, which copied the backing array
     * twice per tick (remove + add) and forced {@link #getAverageTPS()} to box every element into
     * a stream - thousands of allocations per tick once lag compensation reads it per entity.
     */
    private static final int HISTORY_LIMIT = 40;
    private final double[] tpsHistory = new double[HISTORY_LIMIT];
    /** Next write slot in {@link #tpsHistory}. */
    private volatile int historyIndex = 0;
    /** Number of valid samples, grows to {@link #HISTORY_LIMIT} and then stays there. */
    private volatile int historySize = 0;

    public static final int MAX_TPS = 20;
    public static final int FULL_TICK = 50;
    private static final long FULL_TICK_NANOS = 50_000_000L;
    private static final double MIN_TPS = 1.0;
    private static final double MAX_ACCUMULATED_MISSED = 5.0;

    public TPSCalculator() {}

    public void doTick() {
        if (currentTickNanos != null) {
            lastTickNanos = currentTickNanos;
        }

        currentTickNanos = System.nanoTime();
        addToHistory(getTPS());
        clearMissedTicks();
        missedTick();
    }

    private void addToHistory(double tps) {
        tpsHistory[historyIndex] = tps;
        historyIndex = (historyIndex + 1) % HISTORY_LIMIT;
        if (historySize < HISTORY_LIMIT) {
            historySize++;
        }
    }

    public long getMSPT() {
        if (lastTickNanos == null || currentTickNanos == null) return FULL_TICK;
        long diffMs = (currentTickNanos - lastTickNanos) / 1_000_000L;
        return diffMs <= 0 ? 1 : diffMs;
    }

    /**
     * Mean of the recent TPS samples.
     *
     * @return the rolling average, or {@link #MAX_TPS} before any sample has been recorded
     */
    public double getAverageTPS() {
        int size = historySize;
        if (size == 0) {
            return MAX_TPS;
        }
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += tpsHistory[i];
        }
        return sum / size;
    }

    public double getTPS() {
        if (lastTickNanos == null || currentTickNanos == null) return MAX_TPS;
        long diffNanos = currentTickNanos - lastTickNanos;
        if (diffNanos <= 0) return MAX_TPS;
        double tps = 1_000_000_000.0 / (double) diffNanos;
        return Math.min(tps, MAX_TPS);
    }

    public void missedTick() {
        if (lastTickNanos == null) return;

        long diffNanos = currentTickNanos - lastTickNanos;
        if (diffNanos <= 0) return;
        double missedTicks = ((double) diffNanos / (double) FULL_TICK_NANOS) - 1.0;
        if (missedTicks > 0) allMissedTicks += missedTicks;
        if (allMissedTicks > MAX_ACCUMULATED_MISSED) allMissedTicks = MAX_ACCUMULATED_MISSED;
    }

    /**
     * The most pessimistic of the instantaneous and the averaged TPS, clamped to a sane range.
     *
     * @return a TPS value suitable for scaling gameplay timings
     */
    public double getMostAccurateTPS() {
        double tps = Math.min(getTPS(), getAverageTPS());
        if (tps < MIN_TPS) return MIN_TPS;
        if (tps > MAX_TPS) return MAX_TPS;
        return tps;
    }

    public double getAllMissedTicks() {
        return allMissedTicks;
    }

    public int applicableMissedTicks() {
        return (int) Math.floor(allMissedTicks);
    }

    public void clearMissedTicks() {
        allMissedTicks -= applicableMissedTicks();
    }

    public void resetMissedTicks() {
        allMissedTicks = 0;
    }
}
