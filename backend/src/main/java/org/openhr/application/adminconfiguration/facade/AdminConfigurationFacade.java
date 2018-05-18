package org.openhr.application.adminconfiguration.facade;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;

public interface AdminConfigurationFacade {
  AllowanceSettings getAllowanceSettings();

  AllowanceSettings updateAllowanceSettings(AllowanceSettings allowanceSettings);
}
