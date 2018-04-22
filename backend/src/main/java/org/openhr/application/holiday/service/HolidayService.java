package org.openhr.application.holiday.service;

import java.time.LocalDate;

public interface HolidayService {
  long getWorkingDaysInBetween(LocalDate startDate, LocalDate endDate);
}
