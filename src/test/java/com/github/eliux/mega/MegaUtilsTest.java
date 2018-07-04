package com.github.eliux.mega;

import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Assert;
import org.junit.Test;

public class MegaUtilsTest {

  @Test
  public void parseFileDateShouldBeOk() {
    //Given
    String dateStr = "04May2018 17:54:11";

    //When
    final LocalDateTime date = MegaUtils.parseFileDate(dateStr);

    //Then
    Assert.assertNotNull(date);
    Assert.assertEquals(04, date.getDayOfMonth());
    Assert.assertEquals(Month.MAY, date.getMonth());
    Assert.assertEquals(2018, date.getYear());
    Assert.assertEquals(17, date.getHour());
    Assert.assertEquals(54, date.getMinute());
    Assert.assertEquals(11, date.getSecond());
  }

  @Test
  public void isEmailShouldBeOk() {
    Assert.assertTrue(MegaUtils.isEmail("user@doma.in"));
    Assert.assertTrue(MegaUtils.isEmail("eliecerhdz@gmail.com"));
    Assert.assertTrue(MegaUtils.isEmail("a@b.xx"));
  }

  @Test
  public void isEmailShouldFail() {
    Assert.assertFalse(MegaUtils.isEmail("/user@doma.in"));
    Assert.assertFalse(MegaUtils.isEmail("eliecerhdz*@gmail.com"));
    Assert.assertFalse(MegaUtils.isEmail("a@b.toolong"));
  }

  @Test
  public void isDirectoryShouldBeOk() {
    Assert.assertTrue(MegaUtils.isDirectory("/path"));
    Assert.assertTrue(MegaUtils.isDirectory("/path/subpath"));
    Assert.assertTrue(MegaUtils.isDirectory("subpath"));
  }

  @Test
  public void isDirectoryShouldFail() {
    Assert.assertFalse(MegaUtils.isDirectory("user@doma.in"));
    //TODO Improve
  }
}
