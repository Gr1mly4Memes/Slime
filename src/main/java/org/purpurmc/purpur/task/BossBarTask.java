package org.purpurmc.purpur.task;

import net.kyori.adventure.bossbar.BossBar;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.purpurmc.purpur.util.MinecraftInternalPlugin;

import java.util.*;

public abstract class BossBarTask extends BukkitRunnable {
    private final Map<UUID, BossBar> bossbars = new HashMap<>();
    private boolean started;

    abstract BossBar createBossBar();

    abstract void updateBossBar(BossBar bossbar, Player player);

    @Override
    public void run() {
        Iterator<Map.Entry<UUID, BossBar>> iter = bossbars.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<UUID, BossBar> entry = iter.next();
            Player player = Bukkit.getPlayer(entry.getKey());
            if (player == null) {
                iter.remove();
                continue;
            }
            try {
                updateBossBar(entry.getValue(), player);
            } catch (Exception e) {
                e.printStackTrace(); // Slime - don't kill scheduler on malformed MiniMessage / Adventure error
            }
        }
    }

    @Override
    public void cancel() {
        super.cancel();
        new HashSet<>(this.bossbars.keySet()).forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                removePlayer(player);
            }
        });
        this.bossbars.clear();
    }

    public boolean removePlayer(Player player) {
        BossBar bossbar = this.bossbars.remove(player.getUniqueId());
        if (bossbar != null) {
            try {
                player.hideBossBar(bossbar);
            } catch (Exception e) {
                // Slime - NeoForge hybrid: Adventure BossBar impl may be missing, don't propagate
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public void addPlayer(Player player) {
        removePlayer(player); // Slime - ensure previous bar cleared before re-adding
        BossBar bossbar = createBossBar();
        try {
            this.updateBossBar(bossbar, player);
            player.showBossBar(bossbar); // Slime - may throw NoSuchElementException if Adventure service not visible on NeoForge
        } catch (Exception e) {
            // Slime - log and don't cache failed bar so toggle remains consistent
            e.printStackTrace();
            return;
        }
        this.bossbars.put(player.getUniqueId(), bossbar);
    }

    public boolean hasPlayer(UUID uuid) {
        return this.bossbars.containsKey(uuid);
    }

    public boolean togglePlayer(Player player) {
        if (removePlayer(player)) {
            return false;
        }
        addPlayer(player);
        return true;
    }

    public void start() {
        stop();
        this.runTaskTimerAsynchronously(new MinecraftInternalPlugin(), 1, 1);
        started = true;
    }

    public void stop() {
        if (started) {
            cancel();
        }
    }

    public static void startAll() {
        RamBarTask.instance().start();
        TPSBarTask.instance().start();
        CompassTask.instance().start();
    }

    public static void stopAll() {
        RamBarTask.instance().stop();
        TPSBarTask.instance().stop();
        CompassTask.instance().stop();
    }

    public static void addToAll(ServerPlayer player) {
        Player bukkit = player.getBukkitEntity();
        if (player.ramBar()) {
            RamBarTask.instance().addPlayer(bukkit);
        }
        if (player.tpsBar()) {
            TPSBarTask.instance().addPlayer(bukkit);
        }
        if (player.compassBar()) {
            CompassTask.instance().addPlayer(bukkit);
        }
    }

    public static void removeFromAll(Player player) {
        RamBarTask.instance().removePlayer(player);
        TPSBarTask.instance().removePlayer(player);
        CompassTask.instance().removePlayer(player);
    }
}
