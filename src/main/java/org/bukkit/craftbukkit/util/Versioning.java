package org.bukkit.craftbukkit.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.SharedConstants;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Versioning {
    private static final String BUKKIT_VERSION;
    private static final String API_VERSION;

    static {
        String bukkitVersion = "Unknown-Version";
        String apiVersion = null;
        // Slime start - NeoForge hybrid classloader fix: Bukkit.class.getClassLoader() is TransformerClassLoader which doesn't see apiVersioning.json in mod classpath; try multiple loaders and suppress SEVERE
        InputStream stream = null;
        // try multiple classloaders in order of likelihood to see the resource
        ClassLoader[] loaders = new ClassLoader[] {
            Bukkit.class.getClassLoader(),
            Versioning.class.getClassLoader(),
            Thread.currentThread().getContextClassLoader(),
            ClassLoader.getSystemClassLoader()
        };
        for (ClassLoader cl : loaders) {
            if (cl == null) continue;
            stream = cl.getResourceAsStream("apiVersioning.json");
            if (stream != null) break;
            // also try with leading slash via Class.getResourceAsStream
            stream = Versioning.class.getResourceAsStream("/apiVersioning.json");
            if (stream != null) break;
        }
        try (final InputStream s = stream) {
            if (s == null) {
                throw new IOException("apiVersioning.json not found in classpath");
            }

            final JsonObject jsonObject = new Gson()
                .fromJson(new BufferedReader(new InputStreamReader(s)), JsonObject.class);

            if (jsonObject == null) {
                throw new IOException("apiVersioning.json is not a valid JSON file");
            }

            bukkitVersion = jsonObject.get("version").getAsString();
            apiVersion = jsonObject.get("currentApiVersion").getAsString();
        } catch (final IOException ex) {
            // Slime - don't log SEVERE on NeoForge hybrid where file is merged via JarLoader; fallback to SharedConstants quietly
            Logger.getLogger(Versioning.class.getName()).log(Level.FINE, "Could not get Bukkit version, using fallback", ex);
        }
        // Slime end
        BUKKIT_VERSION = bukkitVersion;
        if (apiVersion == null) {
            apiVersion = SharedConstants.getCurrentVersion().id();
        }
        API_VERSION = apiVersion;
    }

    public static String getBukkitVersion() {
        return BUKKIT_VERSION;
    }

    public static String getCurrentApiVersion() {
        return API_VERSION;
    }
}
