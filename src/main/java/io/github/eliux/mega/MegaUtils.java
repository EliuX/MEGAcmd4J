package io.github.eliux.mega;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static io.github.eliux.mega.Mega.envVars;

public interface MegaUtils {

    DateTimeFormatter MEGA_FILE_DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("ddMMMyyyy HH:mm:ss", Locale.US);

    static LocalDateTime parseFileDate(String dateStr){
        return LocalDateTime.parse(dateStr, MEGA_FILE_DATE_TIME_FORMATTER);
    }

    static void handleResult(int code) {
        switch (code) {
            case 0:
            default:
                //Its Ok
        }
    }

    static int execCmd(String cmd, String... envVars)
            throws java.io.IOException, InterruptedException {
        final Process process = Runtime.getRuntime().exec(cmd);
        return process.waitFor();
    }

    static List<String> execWithOutput(String cmd)
            throws java.io.IOException {
        final Process process = Runtime.getRuntime().exec(cmd, envVars());
        final Scanner scanner = new Scanner(process.getInputStream())
                .useDelimiter("\\A");

        final List<String> result = new ArrayList<>();

        while (scanner.hasNext()) {
            result.add(scanner.next());
        }

        process.destroy();

        return result;
    }
}
