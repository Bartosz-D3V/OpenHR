package org.openhr.application.ics.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import org.openhr.common.util.date.DateUtil;
import org.openhr.common.util.iterable.LocalDateRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IcsServiceImpl implements IcsService {
  private static final String prodId = "-//OpenHR//iCal4j 1.0//EN";
  private static final String icsExtension = ".ics";
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public Calendar createStartEndDateEvents(
      final LocalDateRange localDateRange, final String description) {
    final TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
    final TimeZone timezone = registry.getTimeZone("Europe/Belfast");
    final VTimeZone tz = timezone.getVTimeZone();
    final Calendar calendar = new Calendar();
    final Date startDate = new Date(DateUtil.asDate(localDateRange.getStartDate()));
    final Date endDate = new Date(DateUtil.asDate(localDateRange.getEndDate()));
    final VEvent event = new VEvent(startDate, endDate, description);
    final Uid uid = new Uid(UUID.randomUUID().toString());
    calendar.getProperties().add(new ProdId(prodId));
    calendar.getProperties().add(Version.VERSION_2_0);
    calendar.getProperties().add(CalScale.GREGORIAN);
    event.getProperties().add(tz.getTimeZoneId());
    event.getProperties().add(uid);
    calendar.getComponents().add(event);
    return calendar;
  }

  @Override
  public void generateIcsFile(final Calendar calendar, final VEvent event) {
    final String fileName = String.valueOf(event.getDescription()) + icsExtension;
    final CalendarOutputter calendarOutputter = new CalendarOutputter();
    try {
      final FileOutputStream fileOutputStream = new FileOutputStream(fileName);
      calendarOutputter.output(calendar, fileOutputStream);
    } catch (final IOException e) {
      log.error(e.getLocalizedMessage());
    }
  }
}
