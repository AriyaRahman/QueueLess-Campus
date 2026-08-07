package util;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * file helpers shared by every repository in the storage layer
 */
public final class CSVExportUtil {

    private CSVExportUtil() { }

    /** Makes sure the data directory and the given file both exist. */
    public static void ensureFileExists(String path) {
        try {
            Path filePath = Paths.get(path);
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialise file: " + path, e);
        }
    }

    /** Reads all non-blank lines from a file. Returns an empty list if the file has no content. */
    public static List<String> readLines(String path) {
        ensureFileExists(path);
        List<String> lines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get(path))) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read file: " + path, e);
        }
        return lines;
    }

    /** Overwrites the file with the given lines. */
    public static void writeLines(String path, List<String> lines) {
        ensureFileExists(path);
        try {
            Files.write(Paths.get(path), lines);
        } catch (IOException e) {
            throw new RuntimeException("Could not write file: " + path, e);
        }
    }

    /** Appends a single line to the file. */
    public static void appendLine(String path, String line) {
        ensureFileExists(path);
        try {
            Files.write(Paths.get(path), (line + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Could not append to file: " + path, e);
        }
    }

    /** Escapes a field so commas/newlines inside it do not break the CSV structure. */
    public static String escape(String field) {
        if (field == null) return "";
        String cleaned = field.replace(",", ";").replace("\n", " ").replace("\r", " ");
        return cleaned;
    }
}
