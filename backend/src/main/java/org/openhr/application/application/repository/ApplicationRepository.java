package org.openhr.application.application.repository;

import org.openhr.application.application.dao.ApplicationDAO;
import org.openhr.common.domain.application.Application;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ApplicationRepository {
  private final ApplicationDAO applicationDAO;

  public ApplicationRepository(final ApplicationDAO applicationDAO) {
    this.applicationDAO = applicationDAO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Application getApplication(final long applicationId)
      throws ApplicationDoesNotExistException {
    return applicationDAO.getApplication(applicationId);
  }
}
