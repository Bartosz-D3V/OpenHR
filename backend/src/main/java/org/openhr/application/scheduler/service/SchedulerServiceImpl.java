package org.openhr.application.scheduler.service;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class SchedulerServiceImpl implements SchedulerService {
  @Override
  public void schedule(final JobDetail jobDetail, final Trigger trigger) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.scheduleJob(jobDetail, trigger);
  }

  @Override
  public void unschedule(final TriggerKey triggerKey) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.unscheduleJob(triggerKey);
  }

  @Override
  public void cancel(final JobKey jobKey) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.deleteJob(jobKey);
  }

  @Override
  public boolean jobScheduled(final TriggerKey triggerKey) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    return scheduler.checkExists(triggerKey);
  }

  @Override
  public Trigger getTrigger(final TriggerKey triggerKey) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    return scheduler.getTrigger(triggerKey);
  }

  @Override
  public JobDetail getJob(final JobKey jobKey) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    return scheduler.getJobDetail(jobKey);
  }

  @Override
  public void replaceJob(final JobDetail jobDetail) throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.addJob(jobDetail, true);
  }

  @Override
  public void rescheduleJob(final TriggerKey triggerKey, final Trigger trigger)
      throws SchedulerException {
    final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    scheduler.rescheduleJob(triggerKey, trigger);
  }
}
