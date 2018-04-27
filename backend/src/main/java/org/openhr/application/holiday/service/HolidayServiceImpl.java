package org.openhr.application.holiday.service;

import java.time.LocalDate;
import java.util.Set;
import org.openhr.api.bankholidays.domain.BankHoliday;
import org.openhr.api.bankholidays.service.BankHolidaysService;
import org.openhr.common.util.date.DateRangeUtil;
import org.openhr.common.util.iterable.LocalDateRange;
import org.springframework.stereotype.Service;

@Service
public class HolidayServiceImpl implements HolidayService {
  private final BankHolidaysService bankHolidaysService;

  public HolidayServiceImpl(final BankHolidaysService bankHolidaysService) {
    this.bankHolidaysService = bankHolidaysService;
  }

  @Override
  public long getWorkingDaysInBetween(final LocalDate startDate, final LocalDate endDate) {
    final long diffInclFreeDays = DateRangeUtil.diffDaysInclBoth(startDate, endDate);
    final Set<BankHoliday> bankHolidays =
        bankHolidaysService.getBankHolidays("England").getEvents();
    long freeDays = DateRangeUtil.getWeekendDaysBetween(startDate, endDate);
    final LocalDateRange dateRange = new LocalDateRange(startDate, endDate);
    for (final LocalDate localDate : dateRange) {
      for (final BankHoliday bankHoliday : bankHolidays) {
        if (localDate.compareTo(bankHoliday.getDate()) == 0) {
          freeDays++;
        }
      }
    }
    return diffInclFreeDays - freeDays;
  }
}
