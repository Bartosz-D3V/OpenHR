package org.openhr.application.ics.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
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
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public Calendar createStartEndDateEvents(
      final LocalDateRange localDateRange, final String title, final String description) {
    final Calendar calendar = new Calendar();
    final Date startDate = new Date(DateUtil.asDate(localDateRange.getStartDate()));
    final Date endDate = new Date(DateUtil.asDate(localDateRange.getEndDate()));
    final VEvent event = new VEvent(startDate, endDate, title);
    final Uid uid = new Uid(UUID.randomUUID().toString());
    calendar.getProperties().add(new ProdId(prodId));
    calendar.getProperties().add(Version.VERSION_2_0);
    calendar.getProperties().add(CalScale.GREGORIAN);
    event.getProperties().add(uid);
    event.getProperties().add(new Description(description));
    calendar.getComponents().add(event);
    return calendar;
  }

  @Override
  public byte[] getICSAsBytes(final Calendar calendar) {
    final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    final CalendarOutputter outputter = new CalendarOutputter();
    try {
      outputter.output(calendar, buffer);
    } catch (final IOException e) {
      log.error(e.getLocalizedMessage());
    }
    return buffer.toByteArray();
  }
}
