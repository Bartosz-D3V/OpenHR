package org.openhr.application.ics.service;

import net.fortuna.ical4j.model.Calendar;
import org.openhr.common.util.iterable.LocalDateRange;

public interface IcsService {
  Calendar createStartEndDateEvents(
      LocalDateRange localDateRange, String title, String description);

  byte[] getICSAsBytes(Calendar calendar);
}
