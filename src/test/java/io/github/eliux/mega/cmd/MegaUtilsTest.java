package io.github.eliux.mega.cmd;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.YEARS;

import io.github.eliux.mega.MegaUtils;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MegaUtils")
public class MegaUtilsTest {

  @DisplayName("MegaUtils#parseFileDate")
  @Test
  public void parseFileDateShouldBeOk() {
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
}
