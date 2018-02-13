package org.openhr.application.holiday.service;

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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayServiceTest {
  private final static BankHoliday newYearsDay = new BankHoliday("New Year", LocalDate.of(2020, 1, 1), null, false);
  private final static BankHoliday christmasDay = new BankHoliday("Christmas Day", LocalDate.of(2020, 12, 24), null, false);
  private static Set<BankHoliday> bankHolidaySet;
  private static BankHolidays bankHolidays = new BankHolidays();

  @Autowired
  private HolidayService holidayService;

  @MockBean
  private BankHolidaysService bankHolidaysService;

  @Before
  public void setUp() {
    bankHolidaySet = new HashSet<>();
    bankHolidaySet.add(newYearsDay);
    bankHolidaySet.add(christmasDay);
    bankHolidays.setEvents(bankHolidaySet);
  }

  @Test
  public void getWorkingDaysBetweenInclShouldReturnDiffDaysIfThereAreNoFreeDaysBetween() {
    bankHolidays.getEvents().clear();
    when(bankHolidaysService.getBankHolidays(anyString())).thenReturn(bankHolidays);

    final long daysDiff = holidayService.getWorkingDaysBetweenIncl(LocalDate.now(), LocalDate.now().plusDays(2));

    assertEquals(3, daysDiff);
  }

  @Test
  public void getWorkingDaysBetweenInclShouldReturnDiffDaysIfThereFreeDaysBetween() {
    when(bankHolidaysService.getBankHolidays(anyString())).thenReturn(bankHolidays);

    final long daysDiff = holidayService.getWorkingDaysBetweenIncl(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5));

    assertEquals(4, daysDiff);
  }
}
