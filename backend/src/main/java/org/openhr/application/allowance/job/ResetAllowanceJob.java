package org.openhr.application.allowance.job;

import org.openhr.application.allowance.service.AllowanceService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ResetAllowanceJob implements Job {
  @Autowired private AllowanceService allowanceService;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public void execute(final JobExecutionContext jobExecutionContext) {
    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    final long numberOfDaysToCarryOver =
        jobExecutionContext.getMergedJobDataMap().getLongValue("numberOfDaysToCarryOver");
    allowanceService.resetUsedAllowance(numberOfDaysToCarryOver);
  }
}
