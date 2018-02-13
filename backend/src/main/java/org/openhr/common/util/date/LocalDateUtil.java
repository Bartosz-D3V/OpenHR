package org.openhr.common.util.date;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LocalDateUtil {
  public static long diffDays(final LocalDate startDate, final LocalDate endDate) {
    return startDate.until(endDate, ChronoUnit.DAYS);
  }

  public static long diffDaysInclEnd(final LocalDate startDate, final LocalDate endDate) {
    return startDate.until(endDate, ChronoUnit.DAYS) + 1;
  }

  public static long diffDaysInclBoth(final LocalDate startDate, final LocalDate endDate) {
    return startDate.until(endDate, ChronoUnit.DAYS) + 2;
  }
}
