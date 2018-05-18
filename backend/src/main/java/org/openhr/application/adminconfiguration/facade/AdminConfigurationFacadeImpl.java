package org.openhr.application.adminconfiguration.facade;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.application.adminconfiguration.service.AdminConfigurationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminConfigurationFacadeImpl implements AdminConfigurationFacade {
  private final AdminConfigurationService adminConfigurationService;

  public AdminConfigurationFacadeImpl(final AdminConfigurationService adminConfigurationService) {
    this.adminConfigurationService = adminConfigurationService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public AllowanceSettings getAllowanceSettings() {
    return adminConfigurationService.getAllowanceSettings();
  }
}
