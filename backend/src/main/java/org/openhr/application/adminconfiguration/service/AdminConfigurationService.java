package org.openhr.application.adminconfiguration.service;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.quartz.SchedulerException;

public interface AdminConfigurationService {
  AllowanceSettings getAllowanceSettings();

  AllowanceSettings updateAllowanceSettings(AllowanceSettings allowanceSettings)
      throws SchedulerException;
}
