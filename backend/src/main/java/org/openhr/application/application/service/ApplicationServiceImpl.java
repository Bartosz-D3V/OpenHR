package org.openhr.application.application.service;

import java.io.IOException;
import java.util.Locale;
import net.fortuna.ical4j.model.Calendar;
import org.openhr.application.application.repository.ApplicationRepository;
import org.openhr.application.ics.service.IcsService;
import org.openhr.common.domain.application.Application;
import org.openhr.common.enumeration.ApplicationType;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.util.iterable.LocalDateRange;
import org.openhr.common.util.stream.StreamUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final IcsService icsService;
  private final MessageSource messageSource;
  private static final String SPACE = " ";
  private static final String BLANK_STRING = "";

  public ApplicationServiceImpl(
      final ApplicationRepository applicationRepository,
      final IcsService icsService,
      final MessageSource messageSource) {
    this.applicationRepository = applicationRepository;
    this.icsService = icsService;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public byte[] getApplicationICSFile(final long applicationId)
      throws ApplicationDoesNotExistException, IOException {
    final Application application = applicationRepository.getApplication(applicationId);
    final LocalDateRange dateRange =
        new LocalDateRange(application.getStartDate(), application.getEndDate());
    final Calendar calendar =
        icsService.createStartEndDateEvents(
            dateRange, getApplicationTitle(applicationId), getApplicationDescription(application));
    return StreamUtil.createFile(icsService.getICSAsBytes(calendar));
  }

  private String getApplicationTitle(final long applicationId)
      throws ApplicationDoesNotExistException {
    final Application application = applicationRepository.getApplication(applicationId);
    switch (application.getApplicationType()) {
      case LEAVE_APPLICATION:
        return "Leave application";
      case DELEGATION_APPLICATION:
        return "Delegation";
    }
    return null;
  }

  private String getApplicationDescription(final Application application)
      throws ApplicationDoesNotExistException {
    String description = BLANK_STRING;
    if (ApplicationType.LEAVE_APPLICATION.equals(application.getApplicationType())) {
      description =
          messageSource.getMessage("leaveapplication.messagetitle", null, Locale.getDefault());
    } else if (ApplicationType.LEAVE_APPLICATION.equals(application.getApplicationType())) {
      description =
          messageSource.getMessage("delegationapplication.messagetitle", null, Locale.getDefault());
    }
    description =
        description
            .concat(System.lineSeparator())
            .concat(messageSource.getMessage("general.capital.from", null, Locale.getDefault()))
            .concat(SPACE)
            .concat(application.getStartDate().toString())
            .concat(SPACE)
            .concat(messageSource.getMessage("general.small.to", null, Locale.getDefault()))
            .concat(SPACE)
            .concat(application.getEndDate().toString());

    return description;
  }
}
