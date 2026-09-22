package gr1mly4memes.launcher.slime.libraries;

import gr1mly4memes.launcher.slime.Main;
import gr1mly4memes.launcher.slime.action.Action;
import gr1mly4memes.launcher.slime.util.FileUtils;
import gr1mly4memes.launcher.slime.util.SHA256;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static gr1mly4memes.launcher.slime.Main.DEBUG;

public class LibrariesDownloadQueue {

    /**
     * Manifest generated at build time. When present it replaces the (expensive) full jar scan,
     * which otherwise has to hash every jar entry on every single boot.
     */
    private static final String MANIFEST = "META-INF/libraries.txt";

    /** How many times a failed extraction is retried before giving up. */
    private static final int MAX_ATTEMPTS = 2;

    public final Set<Libraries> allLibraries = new LinkedHashSet<>();
    private final Set<Libraries> fail = new LinkedHashSet<>();
    public Set<Libraries> need_download = new LinkedHashSet<>();
    public boolean debug = DEBUG;

    public static LibrariesDownloadQueue create() {
        return new LibrariesDownloadQueue();
    }

    private static boolean isTargetFile(JarEntry entry) {
        String name = entry.getName().toLowerCase(java.util.Locale.ROOT);
        return name.endsWith(".jar") || name.endsWith(".zip") || name.endsWith(".txt") || name.endsWith(".lzma");
    }

    /**
     * Collect the library list, preferring the build-time manifest over a full jar scan.
     *
     * @return Construct the final column
     */
    public LibrariesDownloadQueue build() {
        if (!scanFromManifest()) {
            try {
                scanFromJar();
            } catch (IOException e) {
                System.out.println("[Slime] Failed to scan bundled libraries: " + e);
            }
        }
        return this;
    }

    /**
     * Download in the form of a progress bar.
     *
     * <p>Retries are bounded: the previous implementation recursed for as long as any library
     * failed, which turned a permanently missing entry into a {@link StackOverflowError}.
     */
    public void progressBar() {
        collectMissing();

        for (int attempt = 0; attempt < MAX_ATTEMPTS && !need_download.isEmpty(); attempt++) {
            for (Libraries lib : new LinkedHashSet<>(need_download)) {
                File file = new File(Action.LIBRARIES, lib.path);
                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                String url = "META-INF/" + file.getPath().replace("\\", "/");
                if (copyFileFromJar(file, url, lib)) {
                    debug("copyFileFromJar: OK");
                    fail.remove(lib);
                } else {
                    debug("copyFileFromJar: No " + url);
                    fail.add(lib);
                }
            }

            // Only libraries that are still broken need another round.
            need_download.clear();
            collectMissing();
        }

        if (!fail.isEmpty()) {
            System.out.println("[Slime] " + fail.size() + " librar(ies) could not be extracted, the server may fail to start.");
            if (debug) {
                fail.forEach(lib -> System.out.println("[Slime]   missing: " + lib.path));
            }
        }
    }

    /**
     * Recompute which bundled libraries are missing or corrupted on disk.
     */
    private void collectMissing() {
        need_download.clear();
        for (Libraries libraries : allLibraries) {
            File lib = new File(Action.LIBRARIES, libraries.path);
            if (lib.isFile() && lib.length() == libraries.getSize() && SHA256.verify(lib, libraries.getSha256())) {
                continue;
            }
            need_download.add(libraries);
        }
    }

    protected boolean copyFileFromJar(File file, String pathInJar, Libraries lib) {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(pathInJar)) {
            if (is == null) {
                System.out.println("[Slime] The file " + file.getPath() + " doesn't exists in the Slime jar !");
                return false;
            }
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.out.println("[Slime] Failed to extract " + file.getPath() + ": " + e);
            return false;
        }
    }

    /**
     * Read the build-time manifest listing {@code path|sha256|size} per bundled library.
     *
     * @return {@code true} when the manifest was found and parsed
     */
    private boolean scanFromManifest() {
        var lines = FileUtils.readFileFromJar(Main.class.getClassLoader(), MANIFEST);
        if (lines.isEmpty()) {
            return false;
        }
        int parsed = 0;
        for (String line : lines) {
            if (line.isBlank() || line.startsWith("#")) {
                continue;
            }
            Libraries libraries = Libraries.from(line);
            if (libraries == null) {
                System.out.println("[Slime] Skipping malformed libraries manifest entry: " + line);
                continue;
            }
            allLibraries.add(libraries);
            debug("Find the resource: " + libraries);
            parsed++;
        }
        return parsed > 0;
    }

    public void scanFromJar() throws IOException {
        Enumeration<URL> resources = LibrariesDownloadQueue.class.getClassLoader().getResources(Action.META_INF);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            if (!"jar".equals(url.getProtocol())) {
                continue;
            }
            JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
            try (JarFile jarFile = jarConnection.getJarFile()) {
                String entryPrefix = jarConnection.getEntryName();
                if (entryPrefix == null) {
                    continue;
                }
                if (!entryPrefix.endsWith("/")) {
                    entryPrefix = entryPrefix + "/";
                }
                final String prefix = entryPrefix;
                for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements(); ) {
                    JarEntry entry = entries.nextElement();
                    if (entry.isDirectory() || !entry.getName().startsWith(prefix) || !isTargetFile(entry)) {
                        continue;
                    }
                    String line = entry.getName().substring(prefix.length());
                    try (InputStream is = Main.class.getClassLoader().getResourceAsStream(entry.getName())) {
                        Libraries libraries = new Libraries(line, SHA256.as(is), entry.getSize());
                        allLibraries.add(libraries);
                        debug("Find the resource: " + libraries);
                    } catch (IOException ignored) {
                        // A stream we cannot read is a library we cannot verify; skip it.
                    }
                }
            }
        }
    }

    public void debug(String log) {
        if (debug) System.out.println(log);
    }
}
