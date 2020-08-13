package io.github.eliux.mega;

import java.time.LocalDate;
import java.util.Optional;

/**
 *This factory is meant for creating DateRange
 */
public class LocalDateRange {

    /**
     * Create a DateRange where the startDate is the current date
     * @param endDate
     * @return Optional<DateRange>
     */
    public static Optional<DateRange> of(LocalDate endDate) {
        return Optional.of(new DateRange(LocalDate.now(), endDate));
    }

    public static Optional<DateRange> of(LocalDate startDate, LocalDate endDate) {
        return Optional.of(new DateRange(startDate, endDate));
    }

    /**
     *
     * @param endDateYear duration in year
     * @param endDateMonths duration in month
     * @param endDateDays duration in day
     * @return Optional<DateRange>
     */
    public static Optional<DateRange> in(int endDateYear, int endDateMonths, int endDateDays) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(endDateYear)
                            .plusMonths(endDateMonths)
                            .plusDays(endDateDays);
        return Optional.of(new DateRange(startDate, endDate));
    }

    public static Optional<DateRange> in(LocalDate startDate, int endDateYear, int endDateMonths, int endDateDays) {
        LocalDate endDate = startDate.plusYears(endDateYear)
                .plusMonths(endDateMonths)
                .plusDays(endDateDays);
        return Optional.of(new DateRange(startDate, endDate));
    }
}
