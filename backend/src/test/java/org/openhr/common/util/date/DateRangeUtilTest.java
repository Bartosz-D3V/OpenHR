package org.openhr.common.util.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.common.util.iterable.LocalDateRange;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DateRangeUtilTest {
  private static final LocalDate mockDate1 = LocalDate.of(2020, 1, 1);
  private static final LocalDate mockDate2 = LocalDate.of(2020, 1, 10);

  @Test
  public void diffDaysShouldReturnDifferenceBetweenDatesExcludingStartDate() {
    final long diff = DateRangeUtil.diffDaysInclEnd(mockDate1, mockDate2);

    assertEquals(9, diff);
  }

  @Test
  public void diffDaysInclBothShouldReturnDifferenceBetweenDatesIncludingStartAndEndDate() {
    final long diff = DateRangeUtil.diffDaysInclBoth(mockDate1, mockDate2);

    assertEquals(10, diff);
  }

  @Test
  public void diffDaysInMonthShouldReturnDifferenceBetweenDatesInAGivenMonth() {
    final LocalDate startDate = LocalDate.of(2018, 4, 30);
    final LocalDate endDate = LocalDate.of(2018, 5, 5);
    final LocalDate endDate2 = LocalDate.of(2018, 5, 1);

    assertEquals(1, DateRangeUtil.diffDaysInMonth(startDate, endDate, Month.APRIL));
    assertEquals(5, DateRangeUtil.diffDaysInMonth(startDate, endDate, Month.MAY));
    assertEquals(1, DateRangeUtil.diffDaysInMonth(startDate, endDate2, Month.MAY));
  }

  @Test
  public void getWeekendDaysBetweenShouldReturnNumberOfSaturdaysAndSundaysInDateRange() {
    final LocalDate mockDate1 = LocalDate.of(2018, 1, 4);
    final LocalDate mockDate2 = LocalDate.of(2018, 1, 8);
    final LocalDate mockDate3 = LocalDate.of(2018, 2, 3);
    final LocalDate mockDate4 = LocalDate.of(2018, 2, 4);
    final LocalDate mockDate5 = LocalDate.of(2018, 2, 4);
    final LocalDate mockDate6 = LocalDate.of(2018, 2, 18);

    assertEquals(2, DateRangeUtil.getWeekendDaysBetween(mockDate1, mockDate2));
    assertEquals(2, DateRangeUtil.getWeekendDaysBetween(mockDate3, mockDate4));
    assertEquals(0, DateRangeUtil.getWeekendDaysBetween(mockDate1, mockDate1));
    assertEquals(5, DateRangeUtil.getWeekendDaysBetween(mockDate5, mockDate6));
  }

  @Test
  public void monthInRangeShouldReturnTrueIfRangeCoversGivenMonth() {
    final LocalDate mockDate1 = LocalDate.of(2018, 1, 4);
    final LocalDate mockDate2 = LocalDate.of(2018, 2, 8);
    final LocalDateRange localDateRange = new LocalDateRange(mockDate1, mockDate2);

    assertTrue(DateRangeUtil.monthInRange(localDateRange, Month.JANUARY));
    assertTrue(DateRangeUtil.monthInRange(localDateRange, Month.FEBRUARY));
  }

  @Test
  public void monthInRangeShouldReturnFalseIfRangeDoesNotCoverGivenMonth() {
    final LocalDate mockDate1 = LocalDate.of(2018, 4, 1);
    final LocalDate mockDate2 = LocalDate.of(2018, 5, 2);
    final LocalDateRange localDateRange = new LocalDateRange(mockDate1, mockDate2);

    assertFalse(DateRangeUtil.monthInRange(localDateRange, Month.DECEMBER));
    assertFalse(DateRangeUtil.monthInRange(localDateRange, Month.JUNE));
  }
}
