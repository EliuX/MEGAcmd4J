package io.github.eliux.mega;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

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
}
