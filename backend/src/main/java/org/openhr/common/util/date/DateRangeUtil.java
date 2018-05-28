package org.openhr.common.util.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import org.openhr.common.util.iterable.LocalDateRange;

public final class DateRangeUtil {
  public static long diffDaysInclEnd(final LocalDate startDate, final LocalDate endDate) {
    return startDate.until(endDate, ChronoUnit.DAYS);
  }

  public static long diffDaysInclBoth(final LocalDate startDate, final LocalDate endDate) {
    return startDate.until(endDate, ChronoUnit.DAYS) + 1;
  }

  public static long diffDaysInMonth(
      final LocalDate startDate, final LocalDate endDate, final Month month) {
    long diffDays = 0;
    for (LocalDate date = startDate;
        date.isBefore(endDate) || date.isEqual(endDate);
        date = date.plusDays(1)) {
      if (date.getMonth() == month) {
        diffDays++;
      }
    }
    return diffDays;
  }

  public static long getWeekendDaysBetween(final LocalDate startDate, final LocalDate endDate) {
    LocalDate localDateIt = startDate;
    long freeDays = 0;
    while (localDateIt.isBefore(endDate) || localDateIt.isEqual(endDate)) {
      if (localDateIt.getDayOfWeek().equals(DayOfWeek.SATURDAY)
          || localDateIt.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
        freeDays++;
      }
      localDateIt = localDateIt.plusDays(1L);
    }
    return freeDays;
  }

  public static boolean dateRangeOverlap(
      final LocalDateRange dateRange1, final LocalDateRange dateRange2) {
    return (dateRange2.getStartDate().isBefore(dateRange1.getEndDate())
            || dateRange2.getStartDate().equals(dateRange1.getEndDate()))
        && (dateRange1.getStartDate().isBefore(dateRange2.getEndDate())
            || dateRange1.getStartDate().equals(dateRange2.getEndDate()));
  }

  public static boolean monthInRange(final LocalDateRange dateRange, final Month month) {
    return dateRange.stream().anyMatch(localDate -> localDate.getMonth() == month);
  }
}
