package org.openhr.application.holiday.service;

import org.openhr.api.bankholidays.domain.BankHoliday;
import org.openhr.api.bankholidays.service.BankHolidaysService;
import org.openhr.common.util.date.LocalDateUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class HolidayServiceImpl implements HolidayService {
  private final BankHolidaysService bankHolidaysService;

  public HolidayServiceImpl(final BankHolidaysService bankHolidaysService) {
    this.bankHolidaysService = bankHolidaysService;
  }

  @Override
  public long getWorkingDaysBetweenIncl(final LocalDate startDate, final LocalDate endDate) {
    final long diffInclFreeDays = LocalDateUtil.diffDaysInclEnd(startDate, endDate);
    final Set<BankHoliday> bankHolidays = bankHolidaysService.getBankHolidays("England").getEvents();
    long freeDays = 0;
    LocalDate startDateIt = startDate;
    for (final BankHoliday bankHoliday : bankHolidays) {
      if (bankHoliday.getDate().compareTo(startDateIt) == 0) {
        freeDays++;
      }
      if (freeDays == diffInclFreeDays) {
        break;
      }
    }

    return diffInclFreeDays - freeDays;
  }
}
