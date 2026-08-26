package gr1mly4memes.slime.util;

import net.minecraft.server.MinecraftServer;
import java.util.concurrent.locks.LockSupport;

public class ThreadUtils {

    public static void executeOnMainThread(Runnable runnable) {
        MinecraftServer.getServer().processQueue.add(runnable);
        Thread serverThread = MinecraftServer.getServer().getRunningThread();
        if ("waiting for tasks".equals(LockSupport.getBlocker(serverThread))) {
            LockSupport.unpark(serverThread);
        }
    }
}
