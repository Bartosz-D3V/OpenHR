package org.openhr.common.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalDatePersistenceConverterTest {

  private LocalDatePersistenceConverter localDatePersistenceConverter;

  @Before
  public void setUp() {
    localDatePersistenceConverter = new LocalDatePersistenceConverter();
  }

  @Test
  public void convertToDatabaseColumnShouldReturnNullIfNullWasPassed() {
    Date actualResult = localDatePersistenceConverter.convertToDatabaseColumn(null);

    assertNull(actualResult);
  }

  @Test
  public void convertToDatabaseColumnShouldReturnSQLDate() {
    LocalDate mockDate = LocalDate.of(2000, 3, 5);
    Date actualResult = localDatePersistenceConverter.convertToDatabaseColumn(mockDate);

    assertEquals(Date.valueOf(mockDate), actualResult);
  }

  @Test
  public void convertToEntityAttributeShouldReturnNullIfNullWasPassed() {
    LocalDate actualResult = localDatePersistenceConverter.convertToEntityAttribute(null);

    assertNull(actualResult);
  }

  @Test
  public void convertToEntityAttributeShouldReturnLocalDate() {
    Date mockDate = new Date(1512387552769L);
    LocalDate actualDate = localDatePersistenceConverter.convertToEntityAttribute(mockDate);

    assertEquals(LocalDate.of(2017, 12, 4), actualDate);
  }
}
