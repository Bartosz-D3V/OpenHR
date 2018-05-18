package org.openhr.application.adminconfiguration.service;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.application.adminconfiguration.repository.AdminConfigurationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminConfigurationServiceImpl implements AdminConfigurationService {
  private final AdminConfigurationRepository adminConfigurationRepository;

  public AdminConfigurationServiceImpl(
      final AdminConfigurationRepository adminConfigurationRepository) {
    this.adminConfigurationRepository = adminConfigurationRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public AllowanceSettings getAllowanceSettings() {
    return adminConfigurationRepository.getAllowanceSettings();
  }
}
