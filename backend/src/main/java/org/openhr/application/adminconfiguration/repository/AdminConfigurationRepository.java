package org.openhr.application.adminconfiguration.repository;

import org.openhr.application.adminconfiguration.dao.AdminConfigurationDAO;
import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AdminConfigurationRepository {
  private final AdminConfigurationDAO adminConfigurationDAO;

  public AdminConfigurationRepository(final AdminConfigurationDAO adminConfigurationDAO) {
    this.adminConfigurationDAO = adminConfigurationDAO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public AllowanceSettings getAllowanceSettings() {
    return adminConfigurationDAO.getAllowanceSettings();
  }

  @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
  public AllowanceSettings updateAllowanceSettings(final AllowanceSettings allowanceSettings) {
    return adminConfigurationDAO.updateAllowanceSettings(allowanceSettings);
  }
}
