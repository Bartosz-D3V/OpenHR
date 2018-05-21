package org.openhr.application.adminconfiguration.service;

import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.application.adminconfiguration.repository.AdminConfigurationRepository;
import org.openhr.application.allowance.service.AllowanceService;
import org.openhr.common.util.date.DateUtil;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminConfigurationServiceImpl implements AdminConfigurationService {
  private final AllowanceService allowanceService;
  private final AdminConfigurationRepository adminConfigurationRepository;

  public AdminConfigurationServiceImpl(
      final AllowanceService allowanceService,
      final AdminConfigurationRepository adminConfigurationRepository) {
    this.allowanceService = allowanceService;
    this.adminConfigurationRepository = adminConfigurationRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public AllowanceSettings getAllowanceSettings() {
    return adminConfigurationRepository.getAllowanceSettings();
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public AllowanceSettings updateAllowanceSettings(final AllowanceSettings allowanceSettings)
      throws SchedulerException {
    updateScheduler(allowanceSettings);
    return adminConfigurationRepository.updateAllowanceSettings(allowanceSettings);
  }

  private void updateScheduler(final AllowanceSettings allowanceSettings)
      throws SchedulerException {
    if (allowanceSettings.isAutoResetAllowance()) {
      allowanceService.scheduleResetUsedAllowance(
          DateUtil.asDate(allowanceSettings.getResetDate()));
    } else {
      allowanceService.cancelResetUsedAllowance();
    }
  }
}
