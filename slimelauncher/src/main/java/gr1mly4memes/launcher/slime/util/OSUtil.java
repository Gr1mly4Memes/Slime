package gr1mly4memes.launcher.slime.util;

import java.util.Locale;

public class OSUtil {

    private static volatile OS os = null;

    /**
     * Detect the host operating system.
     *
     * <p>Never returns {@code null}: unknown platforms report {@link OS#UNKNOWN} so callers such as
     * {@code DataParser#parseLaunchArgs()} cannot trip over a silently-null result.
     */
    public static OS getOS() {
        OS result = os;
        if (result != null) {
            return result;
        }
        String operSys = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        // macOS must be tested before the generic "nix/nux" branch: its os.name is "Mac OS X"/"Darwin".
        if (operSys.contains("win")) {
            result = OS.WINDOWS;
        } else if (operSys.contains("mac") || operSys.contains("darwin")) {
            result = OS.MAC;
        } else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix") || operSys.contains("bsd")) {
            result = OS.LINUX;
        } else if (operSys.contains("sunos")) {
            result = OS.SOLARIS;
        } else {
            result = OS.UNKNOWN;
        }
        os = result;
        return result;
    }

    public enum OS {
        WINDOWS, LINUX, MAC, SOLARIS, UNKNOWN;

        public boolean isWindows() {
            return this == WINDOWS;
        }
    }

    private OSUtil() {
    }
}
