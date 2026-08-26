package gr1mly4memes.slime.util;
public class ProxyUtils {

    public static boolean useProxy() {
        return org.spigotmc.SpigotConfig.bungee || io.papermc.paper.configuration.GlobalConfiguration.get().proxies.velocity.enabled;
    }
}
