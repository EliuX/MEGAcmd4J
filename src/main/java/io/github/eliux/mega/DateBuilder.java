package io.github.eliux.mega;

import io.github.eliux.mega.error.MegaExpireDateRequiredException;
import io.github.eliux.mega.error.MegaInvalidExpireDateException;

import java.time.LocalDate;
import java.time.Period;

public class DateBuilder {

    private LocalDate currentDate;
    private LocalDate targetDate;

    public DateBuilder() {
        currentDate = LocalDate.now();
    }

    public DateBuilder setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
        return this;
    }

    public DateBuilder expireAt(LocalDate expireDate) {
        this.targetDate = expireDate;
        return this;
    }

    public DateBuilder expireIn(int year, int months, int days) {
        this.targetDate = this.currentDate
                .plusYears(year)
                .plusMonths(months)
                .plusDays(days);
        return this;
    }

    public String build() {
        if (this.targetDate == null) throw new MegaExpireDateRequiredException();

        if (this.currentDate.isAfter(this.targetDate))
            throw new MegaInvalidExpireDateException("The target date cannot be before the current date");

        Period period = Period.between(this.currentDate, this.targetDate);

        return String.format("%dy%dm%dd", period.getYears(), period.getMonths(), period.getDays());
    }
}
