package io.github.eliux.mega;

import io.github.eliux.mega.error.MegaInvalidDateRangeException;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

/**
 * This class can generate a range using a start and endDate
 */
public class DateRange {

    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = Optional.ofNullable(startDate).orElseThrow(() -> new MegaInvalidDateRangeException("The start date is missiong"));
        this.endDate = Optional.ofNullable(endDate).orElseThrow(() -> new MegaInvalidDateRangeException("The end date is missing"));

        this.validate();
    }

    /**
     *This function generate the range in format %dy%dm%dd using startDate and endDate
     * @return String of %dy%dm%dd
     */
    public String toString() {
        this.validate();
        return convertToString();
    }

    private void validate() {
        if (this.startDate.isAfter(this.endDate)) {
            throw new MegaInvalidDateRangeException("The end date cannot be before the start date");
        }
    }

    private String convertToString() {
        Period period = Period.between(this.startDate, this.endDate);
        return String.format("%dy%dm%dd", period.getYears(), period.getMonths(), period.getDays());
    }
}
