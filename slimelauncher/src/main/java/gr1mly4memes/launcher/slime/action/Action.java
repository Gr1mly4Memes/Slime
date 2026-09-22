/*
 * Copyright (C) Gr1mly4Memes.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package gr1mly4memes.launcher.slime.action;

import gr1mly4memes.launcher.slime.Main;
import gr1mly4memes.launcher.slime.libraries.Libraries;
import gr1mly4memes.launcher.slime.util.DataParser;
import gr1mly4memes.launcher.slime.util.LaunchArgsParser;
import gr1mly4memes.launcher.slime.util.FileUtils;
import gr1mly4memes.launcher.slime.util.MojangEulaUtil;
import gr1mly4memes.launcher.slime.util.SHA256;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Action {

    public static final String LIBRARIES = "libraries";
    public static String META_INF = "META-INF/" + LIBRARIES;

    /**
     * Fallback used only for jars built without the generated {@code META-INF/libraries.txt}.
     * Must stay in sync with {@code installertools_version} in gradle.properties.
     */
    private static final String FALLBACK_INSTALLERTOOLS =
            "net/neoforged/installertools/installertools/4.0.17/installertools-4.0.17-fatjar.jar"
                    + "|5999a84d0d0270ee70678870faf54e9d90ffaa0a3e5783e5b6466da5e457d0a8|814132";

    /** FML bootstrap arguments that are supplied from the bundled args file, not from the user. */
    private static final List<String> FML_ARGS = List.of(
            "--launchTarget", "--fml.neoForgeVersion", "--fml.mcVersion", "--fml.neoFormVersion");

    private static final PrintStream origin = System.out;

    public final String slimeVer;
    public final String neoforgeVer;
    public final String mcVer;
    public final String libPath;
    public final File universalJar;
    public final File MINECRAFT_JAR;
    public final File PATCHED;
    public final File BINPATCH;
    public final List<URL> installerTourls = new ArrayList<>();

    public Action() throws Exception {
        initInstallerLib();
        this.slimeVer = DataParser.getVersion("slime");
        this.neoforgeVer = DataParser.getVersion("neoforge");
        this.mcVer = DataParser.getVersion("minecraft");

        // Fail before touching the filesystem: without a version we cannot build any path below.
        if (slimeVer == null || neoforgeVer == null || mcVer == null) {
            System.out.println("[Slime] There is an error with the installation, the slime / neoforge / minecraft version is not set.");
            System.exit(1);
        }

        this.libPath = new File(LIBRARIES).getAbsolutePath() + "/";
        this.universalJar = new File(libPath + "net/neoforged/neoforge/%s/neoforge-%s-universal.jar".formatted(neoforgeVer, neoforgeVer));
        this.BINPATCH = new File(libPath + "gr1mly4memes/installation/data/client.lzma");
        this.MINECRAFT_JAR = new File(libPath + "net/minecraft/server/%s/server-%s.jar".formatted(mcVer, mcVer));
        this.PATCHED = new File(libPath + "net/neoforged/minecraft-server-patched/%s/minecraft-server-patched-%s.jar".formatted(neoforgeVer, neoforgeVer));

        install();
    }

    public void install() throws Exception {
        copyFileFromJar(BINPATCH, "data/client.lzma", true);
        copyFileFromJar(universalJar, "data/neoforge-%s-universal.jar".formatted(neoforgeVer), false);

        cleanMinecraftJars();
        List<InstallationTask> tasks = new ArrayList<>();
        tasks.add(new ConsoleToolTask(
                "net.neoforged.installertools.ConsoleTool",
                "--task", "PROCESS_MINECRAFT_JAR",
                "--no-mod-manifest",
                "--input", MINECRAFT_JAR.getAbsolutePath(),
                "--output", PATCHED.getAbsolutePath(),
                "--extract-libraries-to", LIBRARIES,
                "--apply-patches", BINPATCH.getAbsolutePath()));

        mute();
        try {
            for (InstallationTask task : tasks) {
                task.execute();
            }
        } finally {
            // Always restore stdout, otherwise a failing install task silences every later message.
            unmute();
        }
    }

    protected void run(String mainClass, String... args) throws Exception {
        List<URL> classPath = installerTourls;
        if (Main.DEBUG) {
            System.out.println("[Slime] Loading " + classPath);
        }
        URLClassLoader loader = URLClassLoader.newInstance(classPath.toArray(new URL[0]));
        try {
            Class.forName(mainClass, true, loader).getDeclaredMethod("main", String[].class).invoke(null, new Object[]{args});
        } finally {
            loader.close();
        }
    }

    protected void mute() throws Exception {
        if (Main.DEBUG) return;
        File out = new File(libPath + "gr1mly4memes/installation", "installationLogs.txt");
        File parent = out.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        System.setOut(new PrintStream(new BufferedOutputStream(Files.newOutputStream(out.toPath())), true, StandardCharsets.UTF_8));
    }

    protected void unmute() {
        if (Main.DEBUG) return;
        System.out.flush();
        System.setOut(origin);
    }

    /**
     * Extract a bundled file, skipping the write when the copy on disk is already identical.
     *
     * @param file      destination on disk
     * @param pathInJar resource path inside the Slime jar
     * @param clearOld  when true, wipe sibling versions before writing (used for binary patches)
     */
    protected void copyFileFromJar(File file, String pathInJar, boolean clearOld) {
        if (file == BINPATCH) {
            file.delete();
        }

        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(pathInJar)) {
            if (is == null) {
                System.out.println("[Slime] The file " + pathInJar + " doesn't exists in the Slime jar !");
                System.exit(1);
                return;
            }

            File parent = file.getParentFile();
            if (parent == null) {
                System.out.println("[Slime] Refusing to extract " + pathInJar + " to a path without a parent directory.");
                return;
            }
            // NB: create the *parent*, not the destination - creating `file` as a directory made
            // every subsequent Files.copy() fail with a silently swallowed IOException.
            Files.createDirectories(parent.toPath());

            // Extract to a temp file first so the bundled stream can be hashed without having to
            // be read twice (an InputStream can only be consumed once).
            File tmp = new File(parent, file.getName() + ".tmp");
            Files.copy(is, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);

            if (file.isFile() && file.length() == tmp.length() && sameContent(file, tmp)) {
                Files.deleteIfExists(tmp.toPath());
                return;
            }

            if (clearOld) {
                clearOldVersions(file);
            } else if (pathInJar.contains("-universal.jar")) {
                file.delete();
            }

            Files.move(tmp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("[Slime] Failed to extract " + pathInJar + ": " + e);
        }
    }

    /**
     * Remove every sibling version directory so stale NeoForge artifacts cannot be picked up.
     */
    private static void clearOldVersions(File file) {
        if (!file.getAbsolutePath().contains("neoforge")) {
            return;
        }
        File versionDir = file.getParentFile();
        File old = versionDir == null ? null : versionDir.getParentFile();
        if (old != null && old.exists()) {
            FileUtils.deleteFolders(old);
        }
    }

    private static boolean sameContent(File a, File b) {
        String hashA = SHA256.as(a);
        return hashA != null && hashA.equals(SHA256.as(b));
    }

    private void initInstallerLib() throws MalformedURLException {
        Libraries libraries = findInstallerTools();
        File file = new File(LIBRARIES, libraries.getPath());
        installerTourls.add(file.toURI().toURL());
    }

    /**
     * Resolve the installertools artifact from the generated manifest so the launcher cannot drift
     * away from {@code installertools_version} in gradle.properties.
     */
    private static Libraries findInstallerTools() {
        for (String line : FileUtils.readFileFromJar(Main.class.getClassLoader(), "META-INF/libraries.txt")) {
            Libraries libraries = Libraries.from(line);
            if (libraries != null && libraries.getPath().contains("installertools")) {
                return libraries;
            }
        }
        Libraries fallback = Libraries.from(FALLBACK_INSTALLERTOOLS);
        if (fallback == null) {
            throw new IllegalStateException("[Slime] Unable to resolve the bundled installertools library.");
        }
        return fallback;
    }

    public void start() throws Exception {
        List<String> forgeArgs = new ArrayList<>();

        // FML bootstrap arguments come from the bundled args file.
        for (String arg : DataParser.launchArgs) {
            for (String fmlArg : FML_ARGS) {
                if (arg.startsWith(fmlArg)) {
                    String[] parts = arg.split(" ", 2);
                    forgeArgs.add(parts[0]);
                    if (parts.length > 1) {
                        forgeArgs.add(parts[1]);
                    }
                    break;
                }
            }
        }

        // Slime start - forward user server args (e.g. --nogui) from the command line to FML Server;
        // DataParser.launchArgs only contains the fml bootstrap args.
        int i = 0;
        while (i < Main.mainArgs.size()) {
            String a = Main.mainArgs.get(i);
            // Skip fml bootstrap args already added above to avoid duplicates
            if (FML_ARGS.contains(a)) {
                i += 2;
                continue;
            }
            // Skip jvm/system args already handled by LaunchArgsParser (-cp and friends)
            if (a.startsWith("--add-opens") || a.startsWith("--add-exports") || a.startsWith("-D")
                    || a.equals("-classpath") || a.equals("-cp")) {
                i += 2;
                continue;
            }
            forgeArgs.add(a);
            i++;
        }
        // Slime end

        LaunchArgsParser.init(DataParser.launchArgs);

        if (!MojangEulaUtil.hasAcceptedEULA()) {
            promptEula();
        }

        Class<?> serverClass = Class.forName("net.neoforged.fml.startup.Server");
        java.lang.reflect.Method mainMethod = serverClass.getDeclaredMethod("main", String[].class);
        mainMethod.invoke(null, (Object) forgeArgs.toArray(String[]::new));
    }

    /**
     * Ask the operator to accept the Minecraft EULA.
     *
     * <p>Previously this only ran on Windows, so a Linux server would boot straight into Mojang's
     * own (much less helpful) refusal, and the read loop threw {@code NoSuchElementException}
     * whenever stdin was closed.
     */
    private static void promptEula() throws IOException {
        if (System.console() == null) {
            System.out.println("[Slime] You need to accept the Minecraft EULA before starting the server.");
            System.out.println("[Slime] Set eula=true in eula.txt and start the server again.");
            System.exit(1);
            return;
        }

        System.out.println("You need to accept the eula to launch your server. Type true to continue.");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                if (!scanner.hasNextLine()) {
                    System.out.println("[Slime] No input available, aborting. Set eula=true in eula.txt to accept the EULA.");
                    System.exit(1);
                    return;
                }
                if ("true".equalsIgnoreCase(scanner.nextLine().trim())) {
                    break;
                }
                System.out.println("You need to accept the eula to launch your server. Type true to continue.");
            }
        }

        MojangEulaUtil.writeInfos(MojangEulaUtil.eulaText());
    }

    private interface InstallationTask {
        void execute() throws Exception;
    }

    private class ConsoleToolTask implements InstallationTask {
        private final String mainClass;
        private final String[] args;

        public ConsoleToolTask(String mainClass, String... args) {
            this.mainClass = mainClass;
            this.args = args;
        }

        @Override
        public void execute() throws Exception {
            run(mainClass, args);
        }
    }

    /**
     * Remove stray {@code minecraft*.jar} files left in {@code libraries/} by older installers,
     * which would otherwise be picked up ahead of the properly patched jar.
     */
    public static void cleanMinecraftJars() {
        File librariesDir = new File(LIBRARIES);
        if (!librariesDir.isDirectory()) {
            return;
        }
        File[] files = librariesDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String name = file.getName().toLowerCase(Locale.ROOT);
            if (file.isFile() && name.endsWith(".jar") && name.startsWith("minecraft")) {
                if (!file.delete()) {
                    System.out.println("[Slime] Unable to delete stale jar: " + file.getPath());
                }
            }
        }
    }
}
