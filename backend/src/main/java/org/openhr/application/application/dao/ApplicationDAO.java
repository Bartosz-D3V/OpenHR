package org.openhr.application.application.dao;

import org.openhr.common.domain.application.Application;
import org.openhr.common.exception.ApplicationDoesNotExistException;

public interface ApplicationDAO {
  Application getApplication(long applicationId) throws ApplicationDoesNotExistException;
}
