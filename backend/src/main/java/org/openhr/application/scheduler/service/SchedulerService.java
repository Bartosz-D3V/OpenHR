package org.openhr.application.scheduler.service;

import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public interface SchedulerService {
  void start() throws SchedulerException;

  void schedule(JobDetail jobDetail, Trigger trigger) throws SchedulerException;

  void unschedule(TriggerKey triggerKey) throws SchedulerException;

  boolean jobScheduled(TriggerKey triggerKey) throws SchedulerException;
}
