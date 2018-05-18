package org.openhr.application.adminconfiguration.dao;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;

public interface AdminConfigurationDAO {
  AllowanceSettings getAllowanceSettings();

  AllowanceSettings updateAllowanceSettings(AllowanceSettings allowanceSettings);
}
