package gr1mly4memes.launcher.slime.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * SHA-256 helpers used to verify launcher libraries.
 *
 * <p>All methods return {@code null} when the digest cannot be computed, so callers can treat
 * a missing/unreadable file the same way as a checksum mismatch.
 */
public class SHA256 {

    private static final String ALGORITHM = "SHA-256";
    private static final HexFormat HEX = HexFormat.of();
    /** Read buffer used when streaming files to the digest. */
    private static final int BUFFER_SIZE = 64 * 1024;

    private SHA256() {
    }

    /**
     * Get the SHA-256 value of this input stream. The stream is fully consumed but <em>not</em> closed,
     * so callers keep ownership of it.
     *
     * @param is stream to digest
     * @return lower-case hex digest, or {@code null} on failure
     */
    public static String as(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            // The stream has to actually be drained through the DigestInputStream, otherwise the
            // digest is finalized over zero bytes and every input yields the same (empty) hash.
            try (DigestInputStream dis = new DigestInputStream(is, digest)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                while (dis.read(buffer) != -1) {
                    // Draining the stream is what feeds the digest.
                }
            }
            return HEX.formatHex(digest.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            return null;
        }
    }

    /**
     * Get the SHA-256 value of the file.
     *
     * @param file file to digest
     * @return lower-case hex digest, or {@code null} on failure
     */
    public static String as(File file) {
        if (file == null) {
            return null;
        }
        try (InputStream is = Files.newInputStream(file.toPath())) {
            return as(is);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get the SHA-256 value of the specified file or path
     *
     * @param filePath path of the file to digest
     * @return lower-case hex digest, or {@code null} on failure
     */
    public static String as(String filePath) {
        return filePath == null ? null : as(new File(filePath));
    }

    /**
     * Check the SHA-256 value of this input stream
     *
     * @param is           stream to digest
     * @param toBeCheckSum expected lower-case hex digest
     * @return {@code true} if the digests match
     */
    public static boolean is(InputStream is, String toBeCheckSum) {
        return matches(as(is), toBeCheckSum);
    }

    /**
     * Check the SHA-256 value of the file
     *
     * @param file         file to digest
     * @param toBeCheckSum expected lower-case hex digest
     * @return {@code true} if the digests match
     */
    public static boolean is(File file, String toBeCheckSum) {
        return matches(as(file), toBeCheckSum);
    }

    /**
     * Check the SHA-256 value of the specified path file
     *
     * @param path         path of the file to digest
     * @param toBeCheckSum expected lower-case hex digest
     * @return {@code true} if the digests match
     */
    public static boolean is(String path, String toBeCheckSum) {
        return matches(as(path), toBeCheckSum);
    }

    private static boolean matches(String actual, String expected) {
        // Treat a null digest as "no match" instead of throwing, so a missing file simply
        // triggers a re-download rather than killing the launcher.
        return expected != null && expected.equalsIgnoreCase(actual);
    }

    /**
     * Verify a file on disk, short-circuiting before the (potentially expensive) digest when the
     * file is missing.
     *
     * @param file         file to verify
     * @param toBeCheckSum expected lower-case hex digest
     * @return {@code true} when the file exists and its digest matches
     */
    public static boolean verify(File file, String toBeCheckSum) {
        return file != null && file.isFile() && matches(as(file), toBeCheckSum);
    }
}
