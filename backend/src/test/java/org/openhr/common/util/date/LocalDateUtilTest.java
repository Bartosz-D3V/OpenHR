package org.openhr.common.util.date;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LocalDateUtilTest {
  private static final LocalDate mockDate1 = LocalDate.of(2020, 1, 1);
  private static final LocalDate mockDate2 = LocalDate.of(2020, 1, 10);

  @Test
  public void diffDaysShouldReturnDifferenceBetweenDatesExcludingStartDate() {
    final long diff = LocalDateUtil.diffDaysInclEnd(mockDate1, mockDate2);

    assertEquals(9, diff);
  }

  @Test
  public void diffDaysInclBothShouldReturnDifferenceBetweenDatesIncludingStartAndEndDate() {
    final long diff = LocalDateUtil.diffDaysInclBoth(mockDate1, mockDate2);

    assertEquals(10, diff);
  }

  @Test
  public void getWeekendDaysBetweenShouldReturnNumberOfSaturdaysAndSundaysInDateRange() {
    final LocalDate mockDate1 = LocalDate.of(2018, 1, 4);
    final LocalDate mockDate2 = LocalDate.of(2018, 1, 8);
    final LocalDate mockDate3 = LocalDate.of(2018, 2, 3);
    final LocalDate mockDate4 = LocalDate.of(2018, 2, 4);
    final LocalDate mockDate5 = LocalDate.of(2018, 2, 4);
    final LocalDate mockDate6 = LocalDate.of(2018, 2, 18);

    assertEquals(2, LocalDateUtil.getWeekendDaysBetween(mockDate1, mockDate2));
    assertEquals(2, LocalDateUtil.getWeekendDaysBetween(mockDate3, mockDate4));
    assertEquals(0, LocalDateUtil.getWeekendDaysBetween(mockDate1, mockDate1));
    assertEquals(5, LocalDateUtil.getWeekendDaysBetween(mockDate5, mockDate6));
  }
}
