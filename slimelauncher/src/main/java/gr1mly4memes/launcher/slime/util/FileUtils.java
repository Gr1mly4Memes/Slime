package gr1mly4memes.launcher.slime.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;

public class FileUtils {

    /**
     * Read a classpath resource line by line.
     *
     * @param classLoader loader used to resolve the resource
     * @param path        resource path
     * @return the lines, or an empty list when the resource is missing or unreadable
     */
    public static List<String> readFileFromJar(ClassLoader classLoader, String path) {
        if (classLoader == null || path == null) {
            return Collections.emptyList();
        }
        try (InputStream stream = classLoader.getResourceAsStream(path)) {
            if (stream == null) {
                return Collections.emptyList();
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                return lines;
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static void deleteFolders(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteFolders(f);
                }
            }
        }
        file.delete();
    }

    public static boolean fileExists(File f, String fName) {
        if (!f.exists()) return false;
        // try-with-resources: the previous version leaked the handle whenever the entry was absent.
        try (JarFile jf = new JarFile(f)) {
            return jf.getJarEntry(fName) != null;
        } catch (IOException e) {
            return false;
        }
    }

    public static void fileWriterMethod(String filepath, String content) {
        try (Writer writer = Files.newBufferedWriter(new File(filepath).toPath(), StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch (IOException ignored) {
        }
    }
}
