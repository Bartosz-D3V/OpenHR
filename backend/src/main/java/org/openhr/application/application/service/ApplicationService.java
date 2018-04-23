package org.openhr.application.application.service;

import java.io.IOException;
import org.openhr.common.exception.ApplicationDoesNotExistException;

public interface ApplicationService {
  byte[] getApplicationICSFile(long applicationId)
      throws ApplicationDoesNotExistException, IOException;
}
