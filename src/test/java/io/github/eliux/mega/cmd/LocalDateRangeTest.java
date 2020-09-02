package io.github.eliux.mega.cmd;

import io.github.eliux.mega.DateRange;
import io.github.eliux.mega.LocalDateRange;
import io.github.eliux.mega.error.MegaInvalidDateRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Mega cmd test LocalDateRange")
@Tag("LocalDateRange")
public class LocalDateRangeTest {

    @Test
    public void testDateRangeWithEmptyStartDateShouldThrowException() {
        //Given
        LocalDate startDate = null;
        LocalDate endDate = LocalDate.of(2020, 8, 7);

        //Then
        assertThrows(MegaInvalidDateRangeException.class, () -> new DateRange(startDate, endDate));
    }

    @Test
    public void testDateRangeWithEmptyEndDateShouldThrowException() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);
        LocalDate endDate = null;

        //Then
        assertThrows(MegaInvalidDateRangeException.class, () -> new DateRange(startDate, endDate));
    }

    @Test
    public void testDateRangeWithStartDateAfterEndDateShouldThrowException() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 7);
        LocalDate endDate = LocalDate.of(2020, 8, 6);

        //Then
        assertThrows(MegaInvalidDateRangeException.class, () -> new DateRange(startDate, endDate));
    }


    @Test
    public void testDateExpireAfter1day() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);
        LocalDate endDate = LocalDate.of(2020, 8, 7);

        //When
        final DateRange dateRange = LocalDateRange.of(startDate, endDate).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("0y0m1d", dateRange.toString());
    }

    @Test
    public void testDateExpireAfter3Months() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);
        LocalDate endDate = LocalDate.of(2020, 11, 4);

        //When
        final DateRange dateRange = LocalDateRange.of(startDate, endDate).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("0y2m29d", dateRange.toString());
    }

    @Test
    public void testDateExpireAfter2Years() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);
        LocalDate endDate = LocalDate.of(2022, 10, 4);

        //When
        final DateRange dateRange = LocalDateRange.of(startDate, endDate).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("2y6m2d", dateRange.toString());
    }

    @Test
    public void testDateExpireIn1day() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 0, 1).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("0y0m1d", dateRange.toString());
    }

    @Test
    public void testDateExpireIn3Months() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 8, 6);


        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 3, 0).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("0y3m0d", dateRange.toString());
    }

    @Test
    public void testDateExpireIn6Months5Days() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 6, 5).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("0y6m5d", dateRange.toString());
    }

    @Test
    public void testDateExpireIn2Years() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 2, 6, 15).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("2y6m15d", dateRange.toString());
    }

    @Test
    public void testDateExpireIn0seconds() {
        //Given
        LocalDate startDate = LocalDate.of(2020, 4, 2);

        //When
        final DateRange dateRange = LocalDateRange.in(startDate, 0, 0, 0).get();

        //Then
        assertNotNull(dateRange);
        assertEquals("0y0m0d", dateRange.toString());
    }
}
