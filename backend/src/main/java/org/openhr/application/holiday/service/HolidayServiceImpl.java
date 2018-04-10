package org.openhr.application.holiday.service;

import java.time.LocalDate;
import java.util.Set;
import org.openhr.api.bankholidays.domain.BankHoliday;
import org.openhr.api.bankholidays.service.BankHolidaysService;
import org.openhr.common.util.date.LocalDateUtil;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService {
  private final BankHolidaysService bankHolidaysService;

  public HolidayServiceImpl(final BankHolidaysService bankHolidaysService) {
    this.bankHolidaysService = bankHolidaysService;
  }

  @Override
  public long getWorkingDaysBetweenIncl(final LocalDate startDate, final LocalDate endDate) {
    final long diffInclFreeDays = LocalDateUtil.diffDaysInclBoth(startDate, endDate);
    final Set<BankHoliday> bankHolidays =
        bankHolidaysService.getBankHolidays("England").getEvents();
    long freeDays = LocalDateUtil.getWeekendDaysBetween(startDate, endDate);
    for (final BankHoliday bankHoliday : bankHolidays) {
      if (bankHoliday.getDate().compareTo(startDate) == 0) {
        freeDays++;
      } else if (bankHoliday.getDate().compareTo(endDate) == 0) {
        freeDays++;
      }
      if (freeDays == diffInclFreeDays) {
        break;
      }
    }

    return diffInclFreeDays - freeDays;
  }
}
