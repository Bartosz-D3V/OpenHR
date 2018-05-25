package org.openhr.application.adminconfiguration.service;

import java.util.Locale;
import org.openhr.application.adminconfiguration.domain.AllowanceSettings;
import org.openhr.application.adminconfiguration.repository.AdminConfigurationRepository;
import org.openhr.application.allowance.service.AllowanceService;
import org.openhr.common.exception.ValidationException;
import org.openhr.common.util.date.DateUtil;
import org.quartz.SchedulerException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminConfigurationServiceImpl implements AdminConfigurationService {
  private final AllowanceService allowanceService;
  private final AdminConfigurationRepository adminConfigurationRepository;
  private final MessageSource messageSource;

  public AdminConfigurationServiceImpl(
      final AllowanceService allowanceService,
      final AdminConfigurationRepository adminConfigurationRepository,
      final MessageSource messageSource) {
    this.allowanceService = allowanceService;
    this.adminConfigurationRepository = adminConfigurationRepository;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public AllowanceSettings getAllowanceSettings() {
    return adminConfigurationRepository.getAllowanceSettings();
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public AllowanceSettings updateAllowanceSettings(final AllowanceSettings allowanceSettings)
      throws SchedulerException, ValidationException {
    validateAllowanceSettings(allowanceSettings);
    updateScheduler(allowanceSettings);
    adminConfigurationRepository.updateAllowanceSettings(allowanceSettings);
    return allowanceSettings;
  }

  private void validateAllowanceSettings(final AllowanceSettings allowanceSettings)
      throws ValidationException {
    if (allowanceSettings.isAutoResetAllowance() && allowanceSettings.getResetDate() == null) {
      final String[] args = new String[1];
      args[0] = "Reset";
      throw new ValidationException(
          messageSource.getMessage("error.validation.dateempty", args, Locale.getDefault()));
    }
  }

  private void updateScheduler(final AllowanceSettings allowanceSettings)
      throws SchedulerException {
    if (allowanceSettings.isAutoResetAllowance()) {
      final long numberOfDaysToCarryOver =
          allowanceSettings.isCarryOverUnusedLeave()
              ? allowanceSettings.getNumberOfDaysToCarryOver()
              : 0;
      allowanceService.scheduleResetUsedAllowance(
          DateUtil.asDate(allowanceSettings.getResetDate()), numberOfDaysToCarryOver);
    } else {
      allowanceService.cancelResetUsedAllowance();
    }
  }
}
