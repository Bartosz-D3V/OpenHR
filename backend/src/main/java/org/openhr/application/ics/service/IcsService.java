package org.openhr.application.ics.service;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.openhr.common.util.iterable.LocalDateRange;

public interface IcsService {
  Calendar createStartEndDateEvents(LocalDateRange localDateRange, String description);

  void generateIcsFile(Calendar calendar, VEvent event);
}
