package org.openhr.converter.date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;


@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

  @Override
  public Date convertToDatabaseColumn(final LocalDate localDate) {
    return (localDate == null ? null : Date.valueOf(localDate));
  }

  @Override
  public LocalDate convertToEntityAttribute(final Date date) {
    return (date == null ? null : date.toLocalDate());
  }
}
