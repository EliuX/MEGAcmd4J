package io.github.eliux.mega;

import io.github.eliux.mega.error.MegaIOException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public interface MegaTestUtils {

    static final List<String> EMPTY_SUFFIX_ENTRIES = Arrays.asList("0", "1", "");

    static String testTextFileName(String namePrefix, Object suffix){
        String suffixStr = EMPTY_SUFFIX_ENTRIES.contains(suffix.toString())
                ? ""
                : "-" + String.valueOf(suffix);
        return String.format("target/%s%s.txt", namePrefix, suffixStr);
    }

    static void createTextFiles(String namePrefix, int numberOfFiles) {
        IntStream.rangeClosed(1, numberOfFiles).forEach(
                i -> createTextFile(
                        testTextFileName(namePrefix, i),
                        "Lorem ipsum dolor...",
                        "This is the content of file #" + i
                )
        );
    }

    static void createTextFile(String filename, String... content) {
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), "utf-8"))) {
                for (String line : content) {
                    writer.write(line);
                }
            }
        } catch (IOException ex) {
            throw new MegaIOException(
                    "Unexpected error while creating file %s: %s",
                    filename, ex.getMessage()
            );
        }
    }

    static void removeTextFiles(String namePrefix, int numberOfFiles){
        IntStream.rangeClosed(1, numberOfFiles).forEach(
                i ->  removeFile(testTextFileName(namePrefix, i))
        );
    }

    static void removeFile(String filename) {
        new File(filename).deleteOnExit();
    }
}
