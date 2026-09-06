package gr1mly4memes.slime;

import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SlimeLogger extends Logger {
    public static final SlimeLogger LOGGER = new SlimeLogger();

    private SlimeLogger() {
        super("Slime", null);
        setParent(Bukkit.getLogger());
        setLevel(Level.ALL);
    }

    public void severe(String msg, Exception exception) {
        this.log(Level.SEVERE, msg, exception);
    }

    public void warning(String msg, Exception exception) {
        this.log(Level.WARNING, msg, exception);
    }

}