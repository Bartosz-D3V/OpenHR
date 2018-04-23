package org.openhr.application.application.facade;

import java.io.IOException;
import org.openhr.common.exception.ApplicationDoesNotExistException;

public interface ApplicationFacade {
  byte[] getApplicationICSFile(long applicationId)
      throws ApplicationDoesNotExistException, IOException;
}
