package io.github.eliux.mega.cmd;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TimeDelayTest")
public class TimeDelayTest {

  @Test
  public void getYearsUsingPeriodShouldBeOk() {
    final TimeDelay timeDelay = TimeDelay.of(Period.ofYears(1));

    Assertions.assertEquals(1L, timeDelay.get(ChronoUnit.YEARS));
  }

  @Test
  public void getDaysUsingPeriodShouldBeOk() {
    final TimeDelay timeDelay = TimeDelay.of(Period.ofDays(2));

    Assertions.assertEquals(2L, timeDelay.get(ChronoUnit.DAYS));
  }


  @Test
  public void getDaysUsingDurationShouldBeOk() {
    final TimeDelay timeDelay = TimeDelay.of(Duration.ofDays(3));

    Assertions.assertEquals(3L, timeDelay.get(ChronoUnit.DAYS));
  }


  @Test
  public void getUnitsShouldSupportYEARS() {
    final TimeDelay timeDelay = TimeDelay.of(Period.ofYears(5));

    Assertions.assertEquals(5, timeDelay.get(ChronoUnit.YEARS));
  }

  @Test
  public void getUnitsShouldSupportMONTHS() {
    final TimeDelay timeDelay = TimeDelay.of(Period.ofMonths(6));

    Assertions.assertEquals(6, timeDelay.get(ChronoUnit.MONTHS));
  }


  @Test
  public void getUnitsShouldSupportHOURS() {
    final TimeDelay timeDelay = TimeDelay.of(Duration.ofHours(6));

    Assertions.assertEquals(6, timeDelay.get(ChronoUnit.HOURS));
  }


  @Test
  public void getUnitsShouldSupportMinutes() {
    final TimeDelay timeDelay = TimeDelay.of(Duration.ofMinutes(7));

    Assertions.assertEquals(7, timeDelay.get(ChronoUnit.MINUTES));
  }


  @Test
  public void getUnitsShouldSupportSECONDS() {
    final TimeDelay timeDelay = TimeDelay.of(Duration.ofSeconds(9));

    Assertions.assertEquals(9, timeDelay.get(ChronoUnit.SECONDS));
  }

  @Test
  public void addToLocalDateShouldBeOk() {
    final LocalDate date = LocalDate.of(2020, 04, 01);
    final TimeDelay timeDelay = TimeDelay.of(Period.ofYears(1).plusMonths(1));

    final LocalDate result = date.plus(timeDelay);

    Assertions.assertEquals(LocalDate.of(2021, 05, 01), result);
  }

  @Test
  public void addToLocalDateTimeShouldBeOk() {
    final LocalDateTime dateTime = LocalDateTime.of(2020, 04, 01, 0, 30, 5, 0);
    final TimeDelay timeDelay = TimeDelay.of(Period.ofDays(3), Duration.ofSeconds(6));

    final LocalDateTime result = dateTime.plus(timeDelay);

    final LocalDateTime expectedResult = LocalDateTime.of(2020, 04, 04, 0, 30, 11, 0);
    Assertions.assertEquals(expectedResult, result);
  }

  @Test
  public void subtractFromLocalDateShouldBeOk() {
    final LocalDate date = LocalDate.of(2020, 04, 01);
    final TimeDelay timeDelay = TimeDelay.of(Period.ofYears(1).plusMonths(2));

    final LocalDate result = date.minus(timeDelay);

    Assertions.assertEquals(LocalDate.of(2019, 02, 01), result);
  }

  @Test
  public void subtractFromLocalDateTimeShouldBeOk() {
    final LocalDateTime dateTime = LocalDateTime.of(2020, 04, 1, 1, 0, 0, 10);
    final TimeDelay timeDelay = TimeDelay.of(Period.ofMonths(5), Duration.ofNanos(10));

    final LocalDateTime result = dateTime.minus(timeDelay);

    final LocalDateTime expectedResult = LocalDateTime.of(2019, 11, 1, 1, 0, 0, 0);
    Assertions.assertEquals(expectedResult, result);
  }

  @DisplayName("TimeDelay#toString should return 0m")
  @Test
  public void toTimeDelayWithZERO() {
    Assertions.assertEquals("0m", TimeDelay.ZERO.toString());
  }


  @DisplayName("TimeDelay#toString should return 12d3h1m")
  @Test
  public void toTimeDelayWithDurationOnly() {
    final TimeDelay timeDelay = TimeDelay.of(
        Duration.ofDays(12).plusHours(3).plusMinutes(1)
    );

    Assertions.assertEquals("12d3h1m", timeDelay.toString());
  }


  @DisplayName("TimeDelay#toString should return 3y11m15d")
  @Test
  public void toTimeDelayWithPeriodOnly() {
    final TimeDelay timeDelay = TimeDelay.of(
        Period.ofYears(3).plusMonths(11).plusDays(15)
    );

    Assertions.assertEquals("3y11m15d", timeDelay.toString());
  }


  @DisplayName("TimeDelay#toString should return 5y2m4d11h1m31s")
  @Test
  public void toTimeDelayWithPeriodAndDuration() {
    final TimeDelay timeDelay = TimeDelay.of(
        Period.ofYears(5).plusMonths(2).plusDays(4),
        Duration.ofHours(11).plusMinutes(1).plusSeconds(31)
    );

    Assertions.assertEquals("5y2m4d11h1m31s", timeDelay.toString());
  }
}
