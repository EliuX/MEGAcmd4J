package io.github.eliux.mega.cmd;

import io.github.eliux.mega.DateBuilder;
import io.github.eliux.mega.error.MegaExpireDateRequiredException;
import io.github.eliux.mega.error.MegaInvalidExpireDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mega cmd test DateBuilder")
@Tag("DateBuilder")
public class DateBuilderTest {

    @Test
    public void testParseDateExpireAfter1day() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 8, 6);
        LocalDate expireDate = LocalDate.of(2020, 8, 7);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireAt(expireDate);

        //Then
        assertNotNull(builder);
        assertEquals("0y0m1d", builder.build());
    }

    @Test
    public void testParseDateExpireAfter3Months() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 8, 6);
        LocalDate expireDate = LocalDate.of(2020, 11, 4);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireAt(expireDate);

        //Then
        assertNotNull(builder);
        assertEquals("0y2m29d", builder.build());
    }

    @Test
    public void testParseDateExpireAfter2Years() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 4, 2);
        LocalDate expireDate = LocalDate.of(2022, 10, 4);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireAt(expireDate);

        //Then
        assertNotNull(builder);
        assertEquals("2y6m2d", builder.build());
    }

    @Test
    public void testParseDateExpireIn1day() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 8, 6);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireIn(0, 0, 1);

        //Then
        assertNotNull(builder);
        assertEquals("0y0m1d", builder.build());
    }

    @Test
    public void testParseDateExpireIn3Months() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 8, 6);


        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireIn(0, 3, 0);

        //Then
        assertNotNull(builder);
        assertEquals("0y3m0d", builder.build());
    }

    @Test
    public void testParseDateExpireIn6Months5Days() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 4, 2);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireIn(0, 6, 5);

        //Then
        assertNotNull(builder);
        assertEquals("0y6m5d", builder.build());
    }

    @Test
    public void testParseDateExpireIn2Years() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 4, 2);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireIn(2, 6, 15);

        //Then
        assertNotNull(builder);
        assertEquals("2y6m15d", builder.build());
    }

    @Test
    public void testParseDateExpireIn0seconds() {
        //Given
        LocalDate currentDate = LocalDate.of(2020, 4, 2);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireIn(0, 0, 0);

        //Then
        assertNotNull(builder);
        assertEquals("0y0m0d", builder.build());
    }

    @Test
    public void testParseInDateWithException() {
        //Given
        final DateBuilder builder = new DateBuilder();

        //Then
        assertNotNull(builder);
        assertThrows(MegaExpireDateRequiredException.class, builder::build);
    }

    @Test
    public void testParseDateInvalidException() {
        //Given
        LocalDate currentDate = LocalDate.of(2021, 4, 2);
        LocalDate expireDate = LocalDate.of(2020, 4, 2);

        //When
        final DateBuilder builder = new DateBuilder()
                .setCurrentDate(currentDate)
                .expireAt(expireDate);

        //Then
        assertNotNull(builder);
        assertThrows(MegaInvalidExpireDateException.class, builder::build);
    }
}
