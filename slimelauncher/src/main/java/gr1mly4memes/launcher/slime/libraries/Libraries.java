package gr1mly4memes.launcher.slime.libraries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Libraries {

    /** Field separator used by the build-time manifest and by {@link #from(String)}. */
    public static final String SEPARATOR = "|";

    String path;
    String sha256;
    long size;

    /**
     * Parse a {@code path|sha256|size} line.
     *
     * @param line manifest line
     * @return the parsed entry, or {@code null} when the line is malformed
     */
    public static Libraries from(String line) {
        if (line == null) {
            return null;
        }
        String[] parts = line.trim().split("\\" + SEPARATOR);
        if (parts.length < 3) {
            return null;
        }
        try {
            return new Libraries(parts[0], parts[1], Long.parseLong(parts[2]));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
