package org.openhr.application.adminconfiguration.facade;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.common.exception.ValidationException;
import org.quartz.SchedulerException;

public interface AdminConfigurationFacade {
  AllowanceSettings getAllowanceSettings();

  AllowanceSettings updateAllowanceSettings(AllowanceSettings allowanceSettings)
      throws SchedulerException, ValidationException;
}
