package io.github.eliux.mega.cmd;

import io.github.eliux.mega.DateRange;
import io.github.eliux.mega.LocalDateRange;
import io.github.eliux.mega.error.MegaInvalidExpireDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mega cmd test LocalDateRange")
@Tag("LocalDateRange")
public class LocalDateRangeTest {

    @Test
    public void testParseDateExpireAfter1day() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);
        LocalDate endDate = LocalDate.of(2020, 8, 7);

        //When
        final DateRange dateRange = LocalDateRange.of(startDate, endDate).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("0y0m1d", dateRange.toString());
    }

    @Test
    public void testParseDateExpireAfter3Months() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);
        LocalDate endDate = LocalDate.of(2020, 11, 4);

        //When
        final DateRange dateRange = LocalDateRange.of(startDate, endDate).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("0y2m29d", dateRange.toString());
    }

    @Test
    public void testParseDateExpireAfter2Years() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);
        LocalDate endDate = LocalDate.of(2022, 10, 4);

        //When
        final DateRange dateRange = LocalDateRange.of(startDate, endDate).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("2y6m2d", dateRange.toString());
    }

    @Test
    public void testParseDateExpireIn1day() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 0, 1).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("0y0m1d", dateRange.toString());
    }

    @Test
    public void testParseDateExpireIn3Months() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);


        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 3, 0).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("0y3m0d", dateRange.toString());
    }

    @Test
    public void testParseDateExpireIn6Months5Days() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 6, 5).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("0y6m5d", dateRange.toString());
    }

    @Test
    public void testParseDateExpireIn2Years() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 2, 6, 15).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("2y6m15d", dateRange.toString());
    }

    @Test
    public void testParseDateExpireIn0seconds() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 0, 0).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertEquals("0y0m0d", dateRange.toString());
    }

    @Test
    public void testParseDateInvalidException() {
        //Given
        LocalDate startDate = LocalDate.of(2021, 4, 2);
        LocalDate endDate = LocalDate.of(2020, 4, 2);

        //When
        final DateRange dateRange = LocalDateRange.of(startDate, endDate).orElse(null);

        //Then
        assertNotNull(dateRange);
        assertThrows(MegaInvalidExpireDateException.class, dateRange::toString);
    }
}
