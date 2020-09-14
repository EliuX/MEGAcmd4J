package io.github.eliux.mega.cmd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class MegaCmdExportTest {


  @DisplayName("MegaCmdExport#setExpireDate using LocalDateTime should produce the right TimeDelay")
  @Test
  public void setExpireDateWithLocalDateTime() {
    final Period period = Period.ofYears(1).plusMonths(11);
    final Duration duration = Duration.ofHours(5).plusSeconds(50);
    final LocalDateTime expirationDate = LocalDateTime.now().plus(period).plus(duration);

    final MegaCmdExport megaCmdExport = new MegaCmdExport("/megacmd/anyfolder")
        .setExpireDate(expirationDate);

    Assertions.assertTrue(megaCmdExport.expirationTimeDelay.isPresent());

    final TimeDelay expirationTimeDelay = megaCmdExport.expirationTimeDelay.get();

    Assertions.assertEquals(expirationTimeDelay.getPeriod().getYears(), period.getYears());
    Assertions.assertEquals(expirationTimeDelay.getPeriod().getMonths(), period.getMonths());
    Assertions.assertEquals(expirationTimeDelay.getDuration().toHours(), duration.toHours());
    Assertions.assertEquals(expirationTimeDelay.getDuration().toMinutes(), duration.toMinutes());
  }

  @DisplayName("MegaCmdExport#setExpireDate using DateTime should produce the right TimeDelay")
  @Test
  public void setExpireDateWithLocalDate() {
    final Period period = Period.ofDays(2);
    final LocalDate endDate = LocalDate.now().plus(period);

    final MegaCmdExport megaCmdExport = new MegaCmdExport("/megacmd/anyfolder")
        .setExpireDate(endDate);

    Assertions.assertTrue(megaCmdExport.expirationTimeDelay.isPresent());

    final TimeDelay expirationTimeDelay = megaCmdExport.expirationTimeDelay.get();

    Assertions.assertEquals(expirationTimeDelay.getPeriod().getDays(), period.getDays());
    Assertions.assertEquals(expirationTimeDelay.getDuration(), Duration.ZERO);
  }
}
