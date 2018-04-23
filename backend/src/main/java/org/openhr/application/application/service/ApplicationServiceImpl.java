package org.openhr.application.application.service;

import java.io.IOException;
import net.fortuna.ical4j.model.Calendar;
import org.openhr.application.application.repository.ApplicationRepository;
import org.openhr.application.ics.service.IcsService;
import org.openhr.common.domain.application.Application;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.util.iterable.LocalDateRange;
import org.openhr.common.util.stream.StreamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationServiceImpl implements ApplicationService {
  private final ApplicationRepository applicationRepository;
  private final IcsService icsService;

  public ApplicationServiceImpl(
      final ApplicationRepository applicationRepository, final IcsService icsService) {
    this.applicationRepository = applicationRepository;
    this.icsService = icsService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public byte[] getApplicationICSFile(final long applicationId)
      throws ApplicationDoesNotExistException, IOException {
    final Application application = applicationRepository.getApplication(applicationId);
    final LocalDateRange dateRange =
        new LocalDateRange(application.getStartDate(), application.getEndDate());
    final Calendar calendar = icsService.createStartEndDateEvents(dateRange, "TEMP");
    return StreamUtil.createFile(icsService.getICSAsBytes(calendar));
  }
}
