package gr1mly4memes.slime;

import com.destroystokyo.paper.util.SneakyThrow;
import gr1mly4memes.slime.commands.GlobalConfigManager;
import gr1mly4memes.slime.commands.SlimeCommand;
import gr1mly4memes.slime.config.ConfigVerify;
import gr1mly4memes.slime.config.GlobalConfig;
import io.papermc.paper.configuration.GlobalConfiguration;
import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

public final class SlimeConfig {
    public static final List<String> CONFIG_HEADER = List.of(
            "This is the main configuration file for Slime.",
            "",
            "Created by Gr1mly4Memes"
    );
    public static final int CURRENT_CONFIG_VERSION = 1;

    private static File configFile;
    public static YamlConfiguration config;
    private static int configVersion;
    public static boolean createWorldSections = true;

    public static void init(final File file) {
        SlimeConfig.configFile = file;
        config = new YamlConfiguration();
        config.options().setHeader(CONFIG_HEADER);
        config.options().copyDefaults(true);

        if (!file.exists()) {
            try {
                boolean is = file.createNewFile();
                if (!is) {
                    throw new IOException("Can't create file");
                }
            } catch (final Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Failure to create slime config", ex);
            }
        } else {
            try {
                config.load(file);
            } catch (final Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Failure to load slime config", ex);
                SneakyThrow.sneaky(ex);
                throw new RuntimeException(ex);
            }
        }

        SlimeConfig.configVersion = SlimeConfig.config.getInt("config-version", CURRENT_CONFIG_VERSION);
        SlimeConfig.config.set("config-version", CURRENT_CONFIG_VERSION);

        GlobalConfigManager.init();

        registerCommand("slime", new SlimeCommand("slime"));
    }

    public static void save() {
        try {
            config.save(SlimeConfig.configFile);
        } catch (final Exception ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Unable to save slime config", ex);
        }
    }

    public static void registerCommand(String name, Command command) {
        MinecraftServer.getServer().server.getCommandMap().register(name, "slime", command);
        MinecraftServer.getServer().server.syncCommands();
    }

    public static void unregisterCommand(String name) {
        name = name.toLowerCase(Locale.ENGLISH).trim();
        MinecraftServer.getServer().server.getCommandMap().getKnownCommands().remove(name);
        MinecraftServer.getServer().server.getCommandMap().getKnownCommands().remove("slime:" + name);
        MinecraftServer.getServer().server.syncCommands();
    }


    // Slime start - Lag compensation
    @GlobalConfig(name = "lag-compensation-enabled", category = {"Lag Compensation"})
    public static boolean lagCompensationEnabled = true;
    @GlobalConfig(name = "block-entity-acceleration", category = {"Lag Compensation"})
    public static boolean blockEntityAcceleration = false;
    @GlobalConfig(name = "block-breaking-acceleration", category = {"Lag Compensation"})
    public static boolean blockBreakingAcceleration = true;
    @GlobalConfig(name = "eating-acceleration", category = {"Lag Compensation"})
    public static boolean eatingAcceleration = true;
    @GlobalConfig(name = "potion-effect-acceleration", category = {"Lag Compensation"})
    public static boolean potionEffectAcceleration = true;
    @GlobalConfig(name = "fluid-acceleration", category = {"Lag Compensation"})
    public static boolean fluidAcceleration = true;
    @GlobalConfig(name = "pickup-acceleration", category = {"Lag Compensation"})
    public static boolean pickupAcceleration = true;
    @GlobalConfig(name = "portal-acceleration", category = {"Lag Compensation"})
    public static boolean portalAcceleration = true;
    @GlobalConfig(name = "time-acceleration", category = {"Lag Compensation"})
    public static boolean timeAcceleration = true;
    // Slime end - Lag compensation
}
