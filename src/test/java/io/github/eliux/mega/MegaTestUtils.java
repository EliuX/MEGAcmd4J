package io.github.eliux.mega;

import io.github.eliux.mega.error.MegaIOException;

import java.io.*;
import java.util.stream.IntStream;

public interface MegaTestUtils {

    static String testTextFileName(String namePrefix, int index){
        String suffix = index > 1 ? "-" + String.valueOf(index) : "";
        return String.format("target/%s%s.txt", namePrefix, suffix);
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
