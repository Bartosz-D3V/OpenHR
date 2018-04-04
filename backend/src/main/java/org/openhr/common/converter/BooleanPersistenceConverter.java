package org.openhr.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanPersistenceConverter implements AttributeConverter<Boolean, Character> {
  @Override
  public Character convertToDatabaseColumn(final Boolean aBoolean) {
    return aBoolean == null ? 'F' : aBoolean ? 'Y' : 'F';
  }

  @Override
  public Boolean convertToEntityAttribute(final Character character) {
    return character != null && (character == 'Y');
  }
}
