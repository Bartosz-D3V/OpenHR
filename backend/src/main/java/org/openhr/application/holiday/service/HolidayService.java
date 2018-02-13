package org.openhr.application.holiday.service;

import java.time.LocalDate;

public interface HolidayService {
  long getWorkingDaysBetweenIncl(LocalDate startDate, LocalDate endDate);
}
