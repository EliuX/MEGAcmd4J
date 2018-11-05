package io.github.eliux.mega;

import static io.github.eliux.mega.Mega.CMD_TTL_ENV_VAR;
import static io.github.eliux.mega.Mega.envVars;

import io.github.eliux.mega.error.MegaConfirmationRequiredException;
import io.github.eliux.mega.error.MegaIOException;
import io.github.eliux.mega.error.MegaInvalidEmailException;
import io.github.eliux.mega.error.MegaInvalidStateException;
import io.github.eliux.mega.error.MegaInvalidTypeException;
import io.github.eliux.mega.error.MegaLoginRequiredException;
import io.github.eliux.mega.error.MegaNodesNotFetchedException;
import io.github.eliux.mega.error.MegaOperationNotAllowedException;
import io.github.eliux.mega.error.MegaResourceNotFoundException;
import io.github.eliux.mega.error.MegaUnexpectedFailureException;
import io.github.eliux.mega.error.MegaWrongArgumentsException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public interface MegaUtils {

  DateTimeFormatter MEGA_FILE_DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern("ddMMMyyyy HH:mm:ss", Locale.US);

  Pattern EMAIL_PATTERN = Pattern.compile(
      "^[\\w]+@[\\w]+\\.[a-zA-Z]{2,6}$",
      Pattern.CASE_INSENSITIVE
  );

  Pattern DIRECTORY_PATTERN = Pattern.compile(
      "[\\/]?[\\p{Alnum}]+(\\/[\\p{Alnum}]+)*",
      Pattern.CASE_INSENSITIVE
  );

  Integer MEGA_TTL = Optional.ofNullable(System.getenv(CMD_TTL_ENV_VAR))
      .map(Integer::new)
      .orElse(20000);

  static LocalDateTime parseFileDate(String dateStr) {
    return LocalDateTime.parse(dateStr, MEGA_FILE_DATE_TIME_FORMATTER);
  }

  static void handleResult(int code) {
    switch (code) {
      case 0:
        //Its Ok
        break;
      case -51:
        throw new MegaWrongArgumentsException();
      case -52:
        throw new MegaInvalidEmailException();
      case -53:
        throw new MegaResourceNotFoundException();
      case -54:
        throw new MegaInvalidStateException();
      case -55:
        throw new MegaInvalidTypeException();
      case -56:
        throw new MegaOperationNotAllowedException();
      case -57:
        throw new MegaLoginRequiredException();
      case -58:
        throw new MegaNodesNotFetchedException();
      case -59:
        throw new MegaUnexpectedFailureException();
      case -60:
        throw new MegaConfirmationRequiredException();
      default:
        throw new MegaUnexpectedFailureException();
    }
  }

  static int execCmd(String cmd, String... envVars)
      throws java.io.IOException, InterruptedException {
    final Process process = Runtime.getRuntime().exec(cmd);

    Boolean succeeded = process.waitFor(MEGA_TTL, TimeUnit.SECONDS);

    return succeeded ? process.exitValue() : -1;
  }

  static List<String> execCmdWithOutput(String cmd)
      throws java.io.IOException {
    final Process process = Runtime.getRuntime().exec(cmd, envVars());

    final Scanner scanner = new Scanner(process.getInputStream())
        .useDelimiter(System.getProperty("line.separator"));

    final List<String> result = new ArrayList<>();

    while (scanner.hasNext()) {
      result.add(scanner.next());
    }

    process.destroy();

    try {
      handleResult(process.waitFor());
    } catch (InterruptedException ex) {
      throw new MegaIOException(
          "The execution of '%s' was interrupted", cmd
      );
    }

    return result;
  }

  static String execCmdWithSingleOutput(String cmd) throws IOException {
    try{
      return execCmdWithOutput(cmd).get(0);
    }catch (IndexOutOfBoundsException ex){
      return "";
    }
  }

  static boolean isEmail(String token) {
    return EMAIL_PATTERN.matcher(token).find();
  }

  static boolean isDirectory(String token) {
    return !isEmail(token) && DIRECTORY_PATTERN.matcher(token).find();
  }
}
