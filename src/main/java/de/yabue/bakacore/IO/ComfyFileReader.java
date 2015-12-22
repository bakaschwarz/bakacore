package de.yabue.bakacore.IO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Diese Klasse dient zum komfortablen Auslesen von Dateien. Komfortabel != Performant.
 */
public class ComfyFileReader {

    private ComfyFileReader() {}

    public static void readInList(final File file, final List<String> list) throws IOException {
        Files.lines(file.toPath()).forEach(list::add);
    }
}
