package gr1mly4memes.launcher.slime.util;

import gr1mly4memes.launcher.slime.Main;
import gr1mly4memes.launcher.slime.util.FileUtils;
import gr1mly4memes.launcher.slime.util.OSUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    public static final List<String> launchArgs = new ArrayList<>();
    private static final HashMap<String, String> versionMap = new HashMap<>();

    public static void parseVersions() {
        versionMap.put("neoforge", FileUtils.readFileFromJar(DataParser.class.getClassLoader(), "versions/neoforge.txt").getFirst());
        versionMap.put("minecraft", FileUtils.readFileFromJar(DataParser.class.getClassLoader(), "versions/minecraft.txt").getFirst());
        versionMap.put("slime", FileUtils.readFileFromJar(DataParser.class.getClassLoader(), "versions/slime.txt").getFirst());

        Main.MCVERSION = versionMap.get("minecraft");
    }

    public static String getVersion(String key) {
        return DataParser.versionMap.get(key);
    }

    public static void parseLaunchArgs() {
        var os = OSUtil.getOS();
        var osName = os.isWindows() ? "win" : "unix";
        launchArgs.addAll(FileUtils.readFileFromJar(DataParser.class.getClassLoader(), "data/" + osName + "_args.txt"));
    }
}
