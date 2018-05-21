package org.openhr.application.scheduler.service;

import org.openhr.common.factory.AutowiringSpringQuartzFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class SchedulerServiceImpl implements SchedulerService {
  private final ApplicationContext applicationContext;

  public SchedulerServiceImpl(final ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
    final AutowiringSpringQuartzFactory jobFactory = new AutowiringSpringQuartzFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Override
  public void start() throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.setJobFactory(springBeanJobFactory());
    scheduler.start();
  }

  @Override
  public void schedule(final JobDetail jobDetail, final Trigger trigger) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.setJobFactory(springBeanJobFactory());
    scheduler.scheduleJob(jobDetail, trigger);
  }

  @Override
  public void unschedule(final TriggerKey triggerKey) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.setJobFactory(springBeanJobFactory());
    scheduler.unscheduleJob(triggerKey);
  }

  @Override
  public boolean jobScheduled(final TriggerKey triggerKey) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.setJobFactory(springBeanJobFactory());
    return scheduler.checkExists(triggerKey);
  }
}
