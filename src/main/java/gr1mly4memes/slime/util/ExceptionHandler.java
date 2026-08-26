package gr1mly4memes.slime.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.BindException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger();
    private static ExceptionHandler INSTANCE;
    private final Map<Class<? extends Throwable>, Consumer<Throwable>> exceptionHandlers = new HashMap<>();

    // ========== Thread stack sampler (full sampling every 100ms, ring buffer) ==========
    private static final int SAMPLE_SIZE = 30;
    private static final long SAMPLE_INTERVAL_MS = 100;
    private static final ThreadInfo[][] SAMPLE_RING = new ThreadInfo[SAMPLE_SIZE][];
    private static final AtomicInteger sampleIndex = new AtomicInteger(0);
    private static volatile boolean samplerStarted = false;

     private static void ensureSamplerStarted() {
        if (samplerStarted) return;
        synchronized (ExceptionHandler.class) {
            if (samplerStarted) return;
            samplerStarted = true;
            SAMPLE_RING[sampleIndex.getAndIncrement() % SAMPLE_SIZE] = ManagementFactory.getThreadMXBean().dumpAllThreads(false, false);
            Thread sampler = new Thread(() -> {
                ThreadMXBean mx = ManagementFactory.getThreadMXBean();
                try {
                    for (int i = 0; i < SAMPLE_SIZE; i++) {
                        Thread.sleep(SAMPLE_INTERVAL_MS);
                        int idx = sampleIndex.getAndIncrement() % SAMPLE_SIZE;
                        SAMPLE_RING[idx] = mx.dumpAllThreads(false, false);
                    }
                } catch (InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                } catch (Exception ignored) {
                }
                samplerStarted = false;
            }, "EH-Sampler");
            sampler.setDaemon(true);
            sampler.setPriority(Thread.MIN_PRIORITY);
            sampler.start();
        }
    }

    public ExceptionHandler() {
        exceptionHandlers.put(OutOfMemoryError.class, this::handleOutOfMemoryError);
        exceptionHandlers.put(ClassNotFoundException.class, this::handleClassNotFoundException);
        exceptionHandlers.put(NoClassDefFoundError.class, this::handleNoClassDefFoundError);
        exceptionHandlers.put(BindException.class, this::handleBindException);
        exceptionHandlers.put(NullPointerException.class, this::handleNullPointerException);
        exceptionHandlers.put(SQLException.class, this::handleSQLException);
        exceptionHandlers.put(ConcurrentModificationException.class, this::handleConcurrentModificationException);
        // Wrap custom handlers of existing threads so our CME detection can also trigger
        Thread.setDefaultUncaughtExceptionHandler(this);
        INSTANCE = this;
        wrapExistingThreadHandlers();
    }

    /**
     * Iterate over all existing threads and wrap their custom UncaughtExceptionHandlers,
     * so CME exceptions can also be caught by our handler
     */
    private static void wrapExistingThreadHandlers() {
        Thread.UncaughtExceptionHandler ourHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (ourHandler == null) return;
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        for (Thread t : threads) {
            Thread.UncaughtExceptionHandler existing = t.getUncaughtExceptionHandler();
            // Only wrap threads that have a custom handler and are not the default handler
            if (existing != null && existing != ourHandler && !(existing instanceof ExceptionHandler)) {
                t.setUncaughtExceptionHandler((thread, throwable) -> {
                    if (throwable instanceof ConcurrentModificationException) {
                        ourHandler.uncaughtException(thread, throwable);
                    }
                    existing.uncaughtException(thread, throwable);
                });
            }
        }
    }

     @Override
    public void uncaughtException(Thread t, Throwable e) {
        // First check if the exception itself or its cause chain contains a CME
        Throwable cme = findCauseOfType(e, ConcurrentModificationException.class);
        Consumer<Throwable> handler = null;
        if (cme != null) {
            // If CME is present, use the CME handler and print full exception info
            LOGGER.error("========== Detected exception (Caused by: ConcurrentModificationException) ==========");
            LOGGER.error("Outer exception: " + e.getClass().getName() + ": " + e.getMessage());
            handleConcurrentModificationException(cme);
        }
        // Still try to match an exact handler
        handler = findHandler(e.getClass());
        if (handler != null) {
            if (cme == null) handler.accept(e);
        } else if (cme == null) {
            LOGGER.error("========== Unhandled exception ==========");
            LOGGER.error("Exception type: " + e.getClass().getName());
            LOGGER.error("Exception message: " + e.getMessage());
            LOGGER.error("Thread: " + t.getName() + " (ID: " + t.getId() + ")");
            LOGGER.error("-------- Full cause chain (Caused by) --------");
            printCauseChain(e);
            LOGGER.error("-------- Full stack trace --------");
            e.printStackTrace();
            LOGGER.error("-------- Relevant code locations (in call order) --------");
            printAllRelevantFrames(e);
            LOGGER.error("========================================");
        }
    }

   /**
     * Find a specific exception type in the cause chain (recursive cause traversal)
     */
    private static <T extends Throwable> T findCauseOfType(Throwable e, Class<T> type) {
        if (e == null) return null;
        if (type.isInstance(e)) return type.cast(e);
        return findCauseOfType(e.getCause(), type);
    }

    /**
     * Static convenience method: check if the cause chain contains a CME and trigger analysis if so.
     * Can be called manually in try-catch or a framework exception handler.
     */
     public static void onException(Throwable e) {
        if (INSTANCE == null) return;
        Throwable cme = findCauseOfType(e, ConcurrentModificationException.class);
        if (cme != null) {
            ensureSamplerStarted();
            ThreadInfo[] snapshot = ManagementFactory.getThreadMXBean().dumpAllThreads(false, false);
            LOGGER.error("========== [Manual call] Detected CME ==========");
            LOGGER.error("Outer exception: " + e.getClass().getName() + ": " + e.getMessage());
            INSTANCE.printCMEAnalysis(cme, snapshot);
        }
    }

    private void printCMEAnalysis(Throwable cme, ThreadInfo[] snapshot) {
        LOGGER.error("Exception message: " + cme.getMessage());
        LOGGER.error("Thread: " + Thread.currentThread().getName() + " (ID: " + Thread.currentThread().getId() + ")");
        LOGGER.error("-------- Full cause chain (Caused by) --------");
        printCauseChain(cme);
        LOGGER.error("-------- Full stack trace --------");
        cme.printStackTrace(System.out);
        LOGGER.error("-------- Relevant code locations (all levels) --------");
        printAllRelevantFrames(cme);
        LOGGER.error("-------- Recent thread samples (last 3s, every 100ms) --------");
        dumpRecentThreadSamples();
        LOGGER.error("-------- Current thread snapshot --------");
        printThreadSnapshot(snapshot);
        LOGGER.error("\n-------- Fix suggestions --------");
        LOGGER.error("1. If you removed an element inside a for-each loop, use Iterator.remove() or switch to CopyOnWriteArrayList / ConcurrentHashMap.");
        LOGGER.error("2. If this is a multi-threaded environment, check whether other thread stacks concurrently access the same collection.");
        LOGGER.error("================================================");
    }

    /**
     * Recursively find a matching exception handler, supporting superclass/interface matching
     */
    private Consumer<Throwable> findHandler(Class<?> clazz) {
        if (clazz == null || clazz == Throwable.class) return null;
        Consumer<Throwable> handler = exceptionHandlers.get(clazz);
        if (handler != null) return handler;
        // Try superclass
        handler = findHandler(clazz.getSuperclass());
        if (handler != null) return handler;
        // Try interfaces
        for (Class<?> iface : clazz.getInterfaces()) {
            handler = findHandler(iface);
            if (handler != null) return handler;
        }
        return null;
    }

    /**
     * Print the full Caused-by chain
     */
    private void printCauseChain(Throwable e) {
        Throwable cause = e;
        int level = 0;
        while (cause != null) {
            if (level > 0) {
                LOGGER.error("  ↓ Caused by (level " + level + "):");
            }
            LOGGER.error("    " + cause.getClass().getName() + ": " + cause.getMessage());
            printRelevantFrames(cause, "      ");
            cause = cause.getCause();
            level++;
        }
    }

    /**
     * Print class-path locations for all non-internal frames in the exception stack
     */
    private void printJarOrClassInfo(Throwable e) {
        printRelevantFrames(e, "");
    }

    private void printRelevantFrames(Throwable e, String indent) {
        Set<String> printed = new HashSet<>();
        for (StackTraceElement element : e.getStackTrace()) {
            if (!isInternalJavaClass(element.getClassName()) && printed.add(element.getClassName())) {
                LOGGER.error(indent + "Class: " + element.getClassName() + " (" + element.getFileName() + ":" + element.getLineNumber() + ")");
                printJarLocation(element.getClassName(), indent);
            }
        }
    }

     /**
      * Print all relevant code locations in the exception chain (scan every level, not just the top)
      */
    private void printAllRelevantFrames(Throwable e) {
        Set<String> printedJars = new HashSet<>();
        Throwable cursor = e;
        int level = 0;
        while (cursor != null) {
            if (level > 0) {
                LOGGER.error("  --- Caused by: " + cursor.getClass().getSimpleName() + " ---");
            }
            for (StackTraceElement element : cursor.getStackTrace()) {
                if (!isInternalJavaClass(element.getClassName()) && printedJars.add(element.getClassName())) {
                    LOGGER.error("  -> " + element.getClassName() + "." + element.getMethodName()
                            + "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
                    printJarLocation(element.getClassName(), "     Source: ");
                }
            }
            cursor = cursor.getCause();
            level++;
        }
    }

    private void printJarLocation(String className, String indent) {
        try {
            Class<?> clazz = Class.forName(className);
            String location = clazz.getProtectionDomain().getCodeSource().getLocation().toString();
            // Simplify path: keep only jar file name or key path
            if (location.endsWith(".jar")) {
                String jarName = location.substring(location.lastIndexOf('/') + 1);
                LOGGER.error(indent + "Jar: " + jarName);
            } else {
                LOGGER.error(indent + "Path: " + location);
            }
        } catch (Exception ignored) {
        }
    }

    private static boolean isInternalJavaClass(String className) {
        return className.startsWith("java.") || className.startsWith("javax.") ||
                className.startsWith("org.w3c.dom.") || className.startsWith("org.xml.") ||
                className.startsWith("com.sun.") || className.startsWith("sun.") ||
                className.startsWith("javafx.") ||
                className.startsWith("jdk.internal.") || className.startsWith("jdk.jfr.");
    }
    
    private void handleOutOfMemoryError(Throwable e) {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;

        LOGGER.error("Not enough memory allocated to the Minecraft server.");
        LOGGER.error("Used memory: " + usedMemory + " MB");
        LOGGER.error("Total allocated memory: " + totalMemory + " MB");
        LOGGER.error("JVM max available memory: " + maxMemory + " MB");
        printJarOrClassInfo(e);
    }

    private void handleClassNotFoundException(Throwable e) {
        LOGGER.error("Java class not found: " + e.getMessage());
        LOGGER.error("Check whether the class is client-only (e.g., contains \"GUI\"); if you believe it is a mod/plugin issue, contact the developer.");
        printJarOrClassInfo(e);
    }

    private void handleNoClassDefFoundError(Throwable e) {
        LOGGER.error("Class not found at runtime: " + e.getMessage());
        LOGGER.error("This may happen because the class existed at compile time but is missing at runtime due to dependency or incomplete classpath issues.");
        printJarOrClassInfo(e);
    }

    private void handleBindException(Throwable e) {
        LOGGER.error("Failed to bind server to: " + e.getMessage());
        LOGGER.error("This may be because the port is already in use or you lack permission to bind to it.");
        LOGGER.error("Check whether another program occupies the same port, or try starting the server on a different port.");
    }

    private void handleNullPointerException(Throwable e) {
        LOGGER.error("Encountered a NullPointerException, likely related to an improperly initialized object.");
        LOGGER.error("Full exception details:");
        e.printStackTrace();
        printJarOrClassInfo(e);
    }

    private void handleSQLException(Throwable e) {
        SQLException sqlException = (SQLException) e;
        LOGGER.error("An error occurred while connecting to the database.");
        LOGGER.error("SQL error code: {}", sqlException.getErrorCode());
        LOGGER.error("SQL message: " + sqlException.getMessage());
        sqlException.printStackTrace();
        printJarOrClassInfo(e);
    }

    /**
     * Enhanced ConcurrentModificationException handling
     * Dumps all current thread stacks to help locate the source of concurrent modification
     */
      private void handleConcurrentModificationException(Throwable e) {
        ensureSamplerStarted();
        ThreadInfo[] snapshot = ManagementFactory.getThreadMXBean().dumpAllThreads(false, false);
        printCMEAnalysis(e, snapshot);
    }

    private void dumpRecentThreadSamples() {
        int total = Math.min(sampleIndex.get(), SAMPLE_SIZE);
        if (total == 0) {
            LOGGER.error("  (Sampler just started, no historical data yet - retry shortly)");
            return;
        }
        LOGGER.error("  Total {} samples (interval {}ms, covering ~{}ms)", total, SAMPLE_INTERVAL_MS, total * SAMPLE_INTERVAL_MS);
        LOGGER.error("  Showing in reverse chronological order, prioritizing threads with collection write operations:");

        // Merge threads seen across all samples, output in reverse chronological order
        Set<String> printedThreads = new LinkedHashSet<>();
        for (int i = 0; i < total; i++) {
            int idx = (sampleIndex.get() - 1 - i) % SAMPLE_SIZE;
            if (idx < 0) idx += SAMPLE_SIZE;
            ThreadInfo[] sample = SAMPLE_RING[idx];
            if (sample == null) continue;
            long sampleTime = System.currentTimeMillis() - i * SAMPLE_INTERVAL_MS;

            for (ThreadInfo info : sample) {
                if (info.getStackTrace().length == 0) continue;
                String tid = info.getThreadName() + ":" + info.getThreadId();
                if (!printedThreads.add(tid)) continue;

                // Only show threads with collection writes (put/remove etc.) or non-internal frames
                boolean hasWriteOp = false;
                boolean hasUserCode = false;
                for (StackTraceElement ste : info.getStackTrace()) {
                    String cn = ste.getClassName();
                    if (!isInternalJavaClass(cn)) hasUserCode = true;
                    if (cn.startsWith("java.util.") && (ste.getMethodName().equals("put") || ste.getMethodName().equals("putVal")
                            || ste.getMethodName().equals("remove") || ste.getMethodName().equals("add")
                            || ste.getMethodName().equals("clear") || ste.getMethodName().equals("resize"))) {
                        hasWriteOp = true;
                    }
                }
                if (!hasWriteOp && !hasUserCode) continue;

                String tag = hasWriteOp ? " <-- contains collection write" : "";
               LOGGER.error("\n  [~" + (i * SAMPLE_INTERVAL_MS) + "ms ago] Thread: {} (State: {}){}", info.getThreadName(), info.getThreadState(), tag);
                for (StackTraceElement ste : info.getStackTrace()) {
                    if (isInternalJavaClass(ste.getClassName())) continue;
                    LOGGER.error("    at {}.{}({}:{})", ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber());
                }
            }
        }
    }

    /**
     * Print thread snapshot, marking collection write operations
     */
    private void printThreadSnapshot(ThreadInfo[] threadInfos) {
        for (ThreadInfo info : threadInfos) {
            if (info.getStackTrace().length == 0) continue;

            LOGGER.error("\n--- Thread: {} (ID: {}, State: {}) ---", info.getThreadName(), info.getThreadId(), info.getThreadState());
            int count = 0;
            boolean hasNonInternal = false;
            for (StackTraceElement ste : info.getStackTrace()) {
                if (!isInternalJavaClass(ste.getClassName())) {
                    hasNonInternal = true;
                    break;
                }
            }

            for (StackTraceElement ste : info.getStackTrace()) {
                // Prefer non-internal frames; if a thread has only internal frames, show all
                if (hasNonInternal && isInternalJavaClass(ste.getClassName())) continue;

                String cn = ste.getClassName();
                String methodSuffix = "";
                if (cn.startsWith("java.util.") && (ste.getMethodName().equals("put") || ste.getMethodName().equals("putVal")
                        || ste.getMethodName().equals("remove") || ste.getMethodName().equals("add")
                        || ste.getMethodName().equals("clear") || ste.getMethodName().equals("resize"))) {
                    methodSuffix = " <-- collection write";
                }

               LOGGER.error("  -> {}.{}({}:{}){}", cn, ste.getMethodName(), ste.getFileName(), ste.getLineNumber(), methodSuffix);
            }
        }
    }

}
