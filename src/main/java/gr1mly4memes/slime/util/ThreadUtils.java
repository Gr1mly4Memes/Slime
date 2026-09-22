package gr1mly4memes.slime.util;

import net.minecraft.server.MinecraftServer;
import java.util.concurrent.locks.LockSupport;

public class ThreadUtils {

    /**
     * Queue work onto the server main thread and wake it up if it is idle.
     *
     * <p>The wake-up used to compare {@code LockSupport.getBlocker(serverThread)} against the
     * literal string {@code "waiting for tasks"}, but the server parks with
     * {@code "waiting for tick or tasks"}, so the comparison never matched and the unpark never
     * happened - queued work silently waited for the next tick deadline or unrelated I/O.
     *
     * <p>Unparking unconditionally is safe: if the server thread is not parked the permit is
     * simply consumed by its next park, and this helper is only used on the (rare) player
     * configuration path rather than per tick.
     */
    public static void executeOnMainThread(Runnable runnable) {
        MinecraftServer server = MinecraftServer.getServer();
        server.processQueue.add(runnable);

        Thread serverThread = server.getRunningThread();
        if (serverThread != null) {
            LockSupport.unpark(serverThread);
        }
    }

    private ThreadUtils() {
    }
}
