package gr1mly4memes.launcher.slime.util;

import gr1mly4memes.launcher.slime.Main;
import gr1mly4memes.launcher.slime.util.FileUtils;
import gr1mly4memes.launcher.slime.util.OSUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParser {

    public static final List<String> launchArgs = new ArrayList<>();
    private static final Map<String, String> versionMap = new HashMap<>();

    public static void parseVersions() {
        versionMap.put("neoforge", firstLine("versions/neoforge.txt"));
        versionMap.put("minecraft", firstLine("versions/minecraft.txt"));
        versionMap.put("slime", firstLine("versions/slime.txt"));

        Main.MCVERSION = versionMap.get("minecraft");
    }

    /**
     * Read the first line of a bundled resource, failing loudly with an actionable message instead
     * of throwing a bare {@link java.util.NoSuchElementException} from {@code List#getFirst()}.
     */
    private static String firstLine(String path) {
        var lines = FileUtils.readFileFromJar(DataParser.class.getClassLoader(), path);
        if (lines.isEmpty()) {
            throw new IllegalStateException("[Slime] Missing or empty " + path + " in the Slime jar, the installation is corrupt.");
        }
        return lines.getFirst();
    }

    public static String getVersion(String key) {
        return DataParser.versionMap.get(key);
    }

    public static void parseLaunchArgs() {
        var os = OSUtil.getOS();
        var osName = os.isWindows() ? "win" : "unix";
        launchArgs.addAll(FileUtils.readFileFromJar(DataParser.class.getClassLoader(), "data/" + osName + "_args.txt"));
    }

    private DataParser() {
    }
}
