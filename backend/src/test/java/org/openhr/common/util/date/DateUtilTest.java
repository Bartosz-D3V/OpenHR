package org.openhr.common.util.date;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DateUtilTest {
  @Test
  public void asDateShouldReturnJava1DateFromLocalDate() throws ParseException {
    final LocalDate localDate = LocalDate.of(2020, Month.JANUARY, 21);
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    final Date expectedDate = sdf.parse("2020/01/21");

    assertEquals(expectedDate, DateUtil.asDate(localDate));
  }

  @Test
  public void asDateShouldReturnJava1DateFromLocalDateTime() throws ParseException {
    final LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JANUARY, 21, 13, 12, 12);
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd:hh:mm:ss");
    final Date expectedDate = sdf.parse("2020/01/21:13:12:12");

    assertEquals(expectedDate, DateUtil.asDate(localDateTime));
  }

  @Test
  public void asLocalDateShouldReturnLocalDateFromDate() throws ParseException {
    final LocalDate localDate = LocalDate.of(2020, Month.JANUARY, 21);
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    final Date date = sdf.parse("2020/01/21");

    assertEquals(localDate, DateUtil.asLocalDate(date));
  }

  @Test
  public void asLocalDateTimeShouldReturnJava1DateFromLocalDateTime() throws ParseException {
    final LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JANUARY, 21, 13, 12, 12);
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd:hh:mm:ss");
    final Date date = sdf.parse("2020/01/21:13:12:12");

    assertEquals(localDateTime, DateUtil.asLocalDateTime(date));
  }
}
