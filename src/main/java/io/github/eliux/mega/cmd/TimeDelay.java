package io.github.eliux.mega.cmd;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeDelay implements TemporalAmount {

  public static final TimeDelay ZERO = new TimeDelay(Period.ZERO, Duration.ZERO);
  static final int HOURS_PER_DAY = 24;
  static final int MINUTES_PER_HOUR = 60;
  static final int SECONDS_PER_MINUTE = 60;
  private static final List<TemporalUnit> SUPPORTED_UNITS =
      Collections.unmodifiableList(
          Arrays.<TemporalUnit>asList(YEARS, MONTHS, DAYS, HOURS, MINUTES, SECONDS, NANOS));
  private final Period period;

  private final Duration duration;

  private TimeDelay(Period period, Duration duration) {
    final Long durationDays = duration.toDays();
    final int days = (durationDays == 0 ? period.getDays() : durationDays.intValue());
    this.period = Period.of(period.getYears(), period.getMonths(), days);
    this.duration = duration.minusDays(duration.toDays());
  }


  static TimeDelay of(Period period, Duration duration) {
    return new TimeDelay(period, duration);
  }


  static TimeDelay of(Period period) {
    return new TimeDelay(period, Duration.ZERO);
  }

  static TimeDelay of(Duration duration) {
    return new TimeDelay(Period.ZERO, duration);
  }

  @Override
  public long get(TemporalUnit unit) {
    if (unit == ChronoUnit.YEARS) {
      return this.period.getYears();
    } else if (unit == ChronoUnit.MONTHS) {
      return this.period.getMonths();
    } else if (unit == ChronoUnit.DAYS) {
      return this.period.getDays();
    } else if (unit == ChronoUnit.MINUTES) {
      return this.duration.toMinutes();
    } else if (unit == SECONDS) {
      return this.duration.getSeconds();
    } else {
      throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
    }
  }

  @Override
  public List<TemporalUnit> getUnits() {
    return SUPPORTED_UNITS;
  }

  @Override
  public Temporal addTo(Temporal temporal) {
    return this.period.addTo(this.duration.addTo(temporal));
  }

  @Override
  public Temporal subtractFrom(Temporal temporal) {
    return this.period.subtractFrom(this.duration.subtractFrom(temporal));
  }

  public Period getPeriod() {
    return period;
  }

  public Duration getDuration() {
    return duration;
  }

  public int toSeconds() {
    return (int) (this.getDuration().getSeconds() % SECONDS_PER_MINUTE);
  }

  public int toMinutes() {
    return (int) (this.getDuration().toMinutes() % MINUTES_PER_HOUR);
  }

  public int toHours() {
    return (int) (this.getDuration().toHours() % HOURS_PER_DAY);
  }


  public String toString() {
    if (this == TimeDelay.ZERO) {
      return "0m";
    } else {
      StringBuilder buf = new StringBuilder();
      final Period period = this.getPeriod();
      if (period != Period.ZERO) {
        if (period.getYears() != 0) {
          buf.append(period.getYears()).append('y');
        }

        if (period.getMonths() != 0) {
          buf.append(period.getMonths()).append('m');
        }

        if (period.getDays() != 0) {
          buf.append(period.getDays()).append('d');
        }
      }
      final Duration duration = this.getDuration();
      if (duration != Duration.ZERO) {
        final long hours = this.toHours();
        if (hours != 0) {
          buf.append(hours).append('h');
        }

        final long minutes = this.toMinutes();
        if (minutes != 0) {
          buf.append(minutes).append('m');
        }

        final int seconds = this.toSeconds();
        if (seconds != 0) {
          buf.append(seconds).append('s');
        }
      }

      return buf.toString();
    }
  }
}
