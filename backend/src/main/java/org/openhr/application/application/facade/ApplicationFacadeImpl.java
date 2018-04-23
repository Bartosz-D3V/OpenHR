package org.openhr.application.application.facade;

import java.io.IOException;
import org.openhr.application.application.service.ApplicationService;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationFacadeImpl implements ApplicationFacade {
  private final ApplicationService applicationService;

  public ApplicationFacadeImpl(final ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public byte[] getApplicationICSFile(final long applicationId)
      throws ApplicationDoesNotExistException, IOException {
    return applicationService.getApplicationICSFile(applicationId);
  }
}
