package org.openhr.application.allowance.job;

import org.openhr.application.allowance.service.AllowanceService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ResetAllowanceJob implements Job {
  @Autowired private AllowanceService allowanceService;

  @Override
  public void execute(final JobExecutionContext jobExecutionContext) {
    final long numberOfDaysToCarryOver =
        jobExecutionContext.getMergedJobDataMap().getLongValue("numberOfDaysToCarryOver");
    allowanceService.resetUsedAllowance(numberOfDaysToCarryOver);
  }
}
