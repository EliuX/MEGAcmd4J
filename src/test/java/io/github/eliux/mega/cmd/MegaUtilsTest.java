package io.github.eliux.mega.cmd;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.eliux.mega.MegaUtils;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MegaUtils")
public class MegaUtilsTest {

  @DisplayName("MegaUtils#parseFileDate well formed datetime like 04May2018 17:54:11")
  @Test
  public void parseFileDateWithTimeShouldBeOk() {
    //Given
    String dateStr = "04May2018 17:54:11";

    //When
    final LocalDateTime date = MegaUtils.parseFileDate(dateStr);

    //Then
    Assertions.assertNotNull(date);
    Assertions.assertEquals(04, date.getDayOfMonth());
    Assertions.assertEquals(Month.MAY, date.getMonth());
    Assertions.assertEquals(2018, date.getYear());
    Assertions.assertEquals(17, date.getHour());
    Assertions.assertEquals(54, date.getMinute());
    Assertions.assertEquals(11, date.getSecond());
  }

  @DisplayName("MegaUtils#parseFileDate malformed datetime like 29Jun2021")
  @Test()
  public void parseFileDateWithoutTimeShouldFail() {
    assertThrows(DateTimeParseException.class,
        () -> MegaUtils.parseFileDate("29Jun2021"));
  }


  @DisplayName("MegaUtils#isEmail")
  @Test
  public void isEmailShouldBeOk() {
    Assertions.assertTrue(MegaUtils.isEmail("user@doma.in"));
    Assertions.assertTrue(MegaUtils.isEmail("eliecerhdz@gmail.com"));
    Assertions.assertTrue(MegaUtils.isEmail("a@b.xx"));
  }

  @DisplayName("MegaUtils#isEmailShouldFail")
  @Test
  public void isEmailShouldFail() {
    Assertions.assertFalse(MegaUtils.isEmail("/user@doma.in"));
    Assertions.assertFalse(MegaUtils.isEmail("eliecerhdz*@gmail.com"));
    Assertions.assertFalse(MegaUtils.isEmail("a@b.toolong"));
  }

  @DisplayName("MegaUtils#isDirectoryShouldBeOk")
  @Test
  public void isDirectoryShouldBeOk() {
    Assertions.assertTrue(MegaUtils.isDirectory("/path"));
    Assertions.assertTrue(MegaUtils.isDirectory("/path/subpath"));
    Assertions.assertTrue(MegaUtils.isDirectory("subpath"));
  }

  @DisplayName("MegaUtils#isDirectoryShouldFail")
  @Test
  public void isDirectoryShouldFail() {
    Assertions.assertFalse(MegaUtils.isDirectory("user@doma.in"));
    //TODO Improve
  }

  @DisplayName("MegaUtils#collectValidCmdOutput should not accept output with banner")
  @Test
  public void collectValidCmdOutputShouldNotAcceptOutputWithStoreBanner() {
    final String OUTPUT_WITH_RUNNING_OUT_OF_STORAGE_BANNER =
            "-------------------------------------------------------------------------------\n" +
            "|                   You are running out of available storage.                   |\n" +
            "|        You can change your account plan to increase your quota limit.         |\n" +
            "|                   See \"help --upgrade\" for further details.                 |\n" +
            "--------------------------------------------------------------------------------\n" +
            "MEGAcmd version: 1.3.0.0: code 1030000\n" +
            "MEGA SDK version: 3.7.0\n";
    final Scanner inputScanner = new Scanner(OUTPUT_WITH_RUNNING_OUT_OF_STORAGE_BANNER)
        .useDelimiter("\n");

    final List<String> result = MegaUtils.collectValidCmdOutput(inputScanner);


    Assertions.assertEquals( 2, result.size());
    Assertions.assertEquals( "MEGAcmd version: 1.3.0.0: code 1030000", result.get(0));
    Assertions.assertEquals("MEGA SDK version: 3.7.0", result.get(1));
  }

  @DisplayName("MegaUtils#collectValidCmdOutput should not trim alike content when banner is over")
  @Test
  public void collectValidCmdOutputShouldNotTrimAlikeContentWhenBannerIsOver() {
    final String CONFUSING_BANNER =
            "-------------------------------------------------------------------------------\n" +
            "|                   You are running out of available storage.                   |\n" +
            "|        You can change your account plan to increase your quota limit.         |\n" +
            "|                   See \"help --upgrade\" for further details.                 |\n" +
            "--------------------------------------------------------------------------------\n" +
            "These are the results\n" +
            "|----------------------------|\n" +
            "|asd | vvvvv | zzzzz   | tttt|\n" +
            "|----------------------------|\n";

    final Scanner inputScanner = new Scanner(CONFUSING_BANNER)
        .useDelimiter("\n");

    final List<String> result = MegaUtils.collectValidCmdOutput(inputScanner);

    Assertions.assertEquals(4, result.size());
    Assertions.assertEquals("These are the results", result.get(0));
    Assertions.assertEquals("|----------------------------|", result.get(1));
    Assertions.assertEquals("|asd | vvvvv | zzzzz   | tttt|", result.get(2));
    Assertions.assertEquals("|----------------------------|", result.get(3));
  }
}
