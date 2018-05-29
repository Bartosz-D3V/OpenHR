package org.openhr.common.util.iterable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.stream.Stream;

public class LocalDateRange implements Iterable<LocalDate> {

  private final LocalDate startDate;
  private final LocalDate endDate;

  public LocalDateRange(final LocalDate startDate, final LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  @Override
  public Iterator<LocalDate> iterator() {
    return stream().iterator();
  }

  public Stream<LocalDate> stream() {
    return Stream.iterate(startDate, d -> d.plusDays(1))
        .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1);
  }
}
