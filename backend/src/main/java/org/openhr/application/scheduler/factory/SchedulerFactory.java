package org.openhr.application.scheduler.factory;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

public final class SchedulerFactory {
  public static SimpleTriggerFactoryBean createTrigger(
      final JobDetail jobDetail, final long pollFrequencyMs) {
    final SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setJobDetail(jobDetail);
    factoryBean.setStartDelay(0L);
    factoryBean.setRepeatInterval(pollFrequencyMs);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    factoryBean.setMisfireInstruction(
        SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
  }

  public static CronTriggerFactoryBean createCronTrigger(
      final JobDetail jobDetail, final String cronExpression) {
    final CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
    factoryBean.setJobDetail(jobDetail);
    factoryBean.setCronExpression(cronExpression);
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
    return factoryBean;
  }

  public static JobDetailFactoryBean createJobDetail(final Class jobClass) {
    final JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
    factoryBean.setJobClass(jobClass);
    factoryBean.setDurability(true);
    return factoryBean;
  }
}
