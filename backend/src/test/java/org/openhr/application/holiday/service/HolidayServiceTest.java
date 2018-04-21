package org.openhr.application.holiday.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.api.bankholidays.domain.BankHoliday;
import org.openhr.api.bankholidays.domain.BankHolidays;
import org.openhr.api.bankholidays.service.BankHolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayServiceTest {
  private static final BankHoliday newYearsDay =
      new BankHoliday("New Year", LocalDate.of(2020, 1, 1), null, false);
  private static final BankHoliday christmasDay =
      new BankHoliday("Christmas Day", LocalDate.of(2020, 12, 24), null, false);
  private static final BankHoliday easterMonday =
      new BankHoliday("Easter Monday", LocalDate.of(2018, 4, 2), null, false);
  private static Set<BankHoliday> bankHolidaySet;
  private static BankHolidays bankHolidays;

  @Autowired private HolidayService holidayService;

  @MockBean private BankHolidaysService bankHolidaysService;

  @Before
  public void setUp() {
    bankHolidays = new BankHolidays();
    bankHolidaySet = new HashSet<>();
    bankHolidaySet.add(newYearsDay);
    bankHolidaySet.add(christmasDay);
    bankHolidaySet.add(easterMonday);
  }

  @Test
  public void getWorkingDaysBetweenInclShouldReturnDiffDaysIfThereAreNoFreeDaysBetween() {
    bankHolidays.setEvents(new HashSet<>());
    when(bankHolidaysService.getBankHolidays(anyString())).thenReturn(bankHolidays);

    final long daysDiff =
        holidayService.getWorkingDaysInBetween(LocalDate.of(2020, 1, 6), LocalDate.of(2020, 1, 9));

    assertEquals(4, daysDiff);
  }

  @Test
  public void getWorkingDaysBetweenInclShouldReturnDiffDaysIfThereFreeDaysBetween() {
    bankHolidays.setEvents(bankHolidaySet);
    when(bankHolidaysService.getBankHolidays(anyString())).thenReturn(bankHolidays);

    final long daysDiff1 =
        holidayService.getWorkingDaysInBetween(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5));
    final long daysDiff2 =
        holidayService.getWorkingDaysInBetween(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1));
    final long daysDiff3 =
        holidayService.getWorkingDaysInBetween(LocalDate.of(2018, 4, 1), LocalDate.of(2018, 4, 7));

    assertEquals(2, daysDiff1);
    assertEquals(0, daysDiff2);
    assertEquals(4, daysDiff3);
  }
}
