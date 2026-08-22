package org.bukkit.craftbukkit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public final class Versioning {
    public static String getBukkitVersion() {
        String result = "Unknown-Version";

        InputStream stream = Bukkit.class.getClassLoader().getResourceAsStream("META-INF/maven/org.spigotmc/spigot-api/pom.properties");
        Properties properties = new Properties();

        if (stream != null) {
            try {
                properties.load(stream);

                result = properties.getProperty("version");
            } catch (IOException ex) {
                Logger.getLogger(Versioning.class.getName()).log(Level.SEVERE, "Could not get Bukkit version!", ex);
            }
        }

        // Slime - fallback for hybrid dev run and custom builds
        if ("Unknown-Version".equals(result)) {
            try (InputStream is = Bukkit.class.getClassLoader().getResourceAsStream("version.properties")) {
                if (is != null) {
                    Properties p = new Properties();
                    p.load(is);
                    String mc = p.getProperty("minecraft_version");
                    String neo = p.getProperty("neoforge_version");
                    if (mc != null) result = mc + "-R0.1-SNAPSHOT" + (neo != null && !neo.contains("${") ? " (Slime " + neo + ")" : "");
                }
            } catch (Exception ignored) {}
        }
        if ("Unknown-Version".equals(result)) {
            try {
                result = net.minecraft.SharedConstants.getCurrentVersion().name() + "-R0.1-SNAPSHOT"; // Slime - last resort MC version
            } catch (Throwable ignored) {}
        }

        return result;
    }
}
