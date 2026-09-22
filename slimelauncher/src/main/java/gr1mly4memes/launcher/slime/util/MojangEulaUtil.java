package gr1mly4memes.launcher.slime.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class MojangEulaUtil {

    private static final File eula = new File("eula.txt");
    private static final File globalEula = new File(System.getProperty("user.home"), "eula.txt");
    private static final String EULA_URL = "https://account.mojang.com/documents/minecraft_eula";

    public static void writeInfos(String eulaText) throws IOException {
        File target = eula;
        File parent = target.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        // UTF-8 explicitly: the previous version used the platform default charset on write but
        // read the file back as UTF-8, which corrupted eula.txt on non-UTF-8 default platforms.
        Files.writeString(target.toPath(), eulaText, StandardCharsets.UTF_8);
    }

    public static boolean hasAcceptedEULA() {
        return hasAccepted(globalEula) || hasAccepted(eula);
    }

    private static boolean hasAccepted(File file) {
        if (!file.isFile()) {
            return false;
        }
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            return lines.contains("eula=true");
        } catch (MalformedInputException e) {
            // Someone wrote the file in a non-UTF-8 encoding; fall back to the platform default
            // instead of failing the whole startup.
            try {
                return Files.readAllLines(file.toPath()).contains("eula=true");
            } catch (IOException ignored) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Build the eula.txt contents shown to the operator.
     */
    public static String eulaText() {
        return "By changing the setting below to true, you are indicating your agreement to our EULA ("
                + EULA_URL + ").\n"
                + java.time.LocalDate.now() + "\n"
                + "eula=true";
    }

    private MojangEulaUtil() {
    }
}
