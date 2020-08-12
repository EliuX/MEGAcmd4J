package io.github.eliux.mega;

import java.time.LocalDate;
import java.util.Optional;

/**
 *
 */
public class LocalDateRange {

    /**
     *
     * @param endDate
     * @return
     */
    public static Optional<DateRange> of(LocalDate endDate) {
        return Optional.of(new DateRange(LocalDate.now(), endDate));
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Optional<DateRange> of(LocalDate startDate, LocalDate endDate) {
        return Optional.of(new DateRange(startDate, endDate));
    }

    /**
     *
     * @param endDateYear
     * @param endDateMonths
     * @param endDateDays
     * @return
     */
    public static Optional<DateRange> in(int endDateYear, int endDateMonths, int endDateDays) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(endDateYear)
                            .plusMonths(endDateMonths)
                            .plusDays(endDateDays);
        return Optional.of(new DateRange(startDate, endDate));
    }

    /**
     *
     * @param startDate
     * @param endDateYear
     * @param endDateMonths
     * @param endDateDays
     * @return
     */
    public static Optional<DateRange> in(LocalDate startDate, int endDateYear, int endDateMonths, int endDateDays) {
        LocalDate endDate = startDate.plusYears(endDateYear)
                .plusMonths(endDateMonths)
                .plusDays(endDateDays);
        return Optional.of(new DateRange(startDate, endDate));
    }
}
