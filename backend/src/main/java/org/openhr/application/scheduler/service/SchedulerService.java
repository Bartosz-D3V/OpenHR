package org.openhr.application.scheduler.service;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public interface SchedulerService {
  void start() throws SchedulerException;

  void stop() throws SchedulerException;

  void schedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException;

  void unschedule(TriggerKey triggerKey) throws SchedulerException;

  void cancel(JobKey jobKey) throws SchedulerException;

  boolean jobScheduled(TriggerKey triggerKey) throws SchedulerException;

  Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException;

  JobDetail getJob(JobKey jobKey) throws SchedulerException;

  void replaceJob(JobDetail jobDetail) throws SchedulerException;

  void rescheduleJob(TriggerKey triggerKey, Trigger trigger) throws SchedulerException;
}
