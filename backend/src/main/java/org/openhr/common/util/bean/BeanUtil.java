package org.openhr.common.util.bean;

import org.springframework.beans.BeanUtils;

public final class BeanUtil {
  public static void copyNotNullProperties(
      final Object source, final Object target, final String... properties) {
    if (source == null) return;
    BeanUtils.copyProperties(source, target, properties);
  }
}
