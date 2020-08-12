package io.github.eliux.mega;

import io.github.eliux.mega.error.MegaExpireDateRequiredException;
import io.github.eliux.mega.error.MegaInvalidExpireDateException;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 */
public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public String parse() {
        if (this.endDate == null) {
            throw new MegaExpireDateRequiredException();
        }

        if (this.startDate.isAfter(this.endDate)) {
            throw new MegaInvalidExpireDateException("The target date cannot be before the current date");
        }

        Period period = Period.between(this.startDate, this.endDate);

        return String.format("%dy%dm%dd", period.getYears(), period.getMonths(), period.getDays());
    }
}
