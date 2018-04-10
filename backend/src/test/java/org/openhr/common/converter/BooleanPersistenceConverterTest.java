package org.openhr.common.converter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooleanPersistenceConverterTest {
  private BooleanPersistenceConverter booleanPersistenceConverter;

  @Before
  public void setUp() {
    this.booleanPersistenceConverter = new BooleanPersistenceConverter();
  }

  @Test
  public void convertToDatabaseColumnShouldReturnYIfTruePassed() {
    assertSame('Y', booleanPersistenceConverter.convertToDatabaseColumn(true));
  }

  @Test
  public void convertToDatabaseColumnShouldReturnFIfFalsePassed() {
    assertSame('F', booleanPersistenceConverter.convertToDatabaseColumn(false));
  }

  @Test
  public void convertToDatabaseColumnShouldReturnFIfNullPassed() {
    assertSame('F', booleanPersistenceConverter.convertToDatabaseColumn(null));
  }

  @Test
  public void convertToEntityAttributeShouldReturnTrueIfYWasPersisted() {
    assertTrue(booleanPersistenceConverter.convertToEntityAttribute('Y'));
  }

  @Test
  public void convertToEntityAttributeShouldReturnFalseIfFWasPersisted() {
    assertFalse(booleanPersistenceConverter.convertToEntityAttribute('F'));
  }

  @Test
  public void convertToEntityAttributeShouldReturnFalseIfNullWasPersisted() {
    assertFalse(booleanPersistenceConverter.convertToEntityAttribute(null));
  }
}
