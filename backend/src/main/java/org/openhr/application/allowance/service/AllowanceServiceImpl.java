package org.openhr.application.allowance.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;
import org.openhr.application.allowance.job.ResetAllowanceJob;
import org.openhr.application.allowance.repository.AllowanceRepository;
import org.openhr.application.holiday.service.HolidayService;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.scheduler.service.SchedulerService;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ValidationException;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AllowanceServiceImpl implements AllowanceService {
  private final AllowanceRepository allowanceRepository;
  private final SubjectService subjectService;
  private final HolidayService holidayService;
  private final SchedulerService schedulerService;;
  private final MessageSource messageSource;

  public AllowanceServiceImpl(
      final AllowanceRepository allowanceRepository,
      final SubjectService subjectService,
      final HolidayService holidayService,
      final SchedulerService schedulerService,
      final MessageSource messageSource) {
    this.allowanceRepository = allowanceRepository;
    this.subjectService = subjectService;
    this.holidayService = holidayService;
    this.schedulerService = schedulerService;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getLeftAllowanceInDays(final long subjectId) {
    return allowanceRepository.getAllowance(subjectId)
        - allowanceRepository.getUsedAllowance(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getAllowance(final long subjectId) {
    return allowanceRepository.getAllowance(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getUsedAllowance(final long subjectId) {
    return allowanceRepository.getUsedAllowance(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Subject subtractDaysFromSubjectAllowanceExcludingFreeDays(
      final Subject subject, final LeaveApplication leaveApplication) throws ValidationException {
    final long allowanceToSubtract =
        holidayService.getWorkingDaysInBetween(
            leaveApplication.getStartDate(), leaveApplication.getEndDate());
    final long newUsedAllowance = getUsedAllowance(subject.getSubjectId()) + allowanceToSubtract;
    if (allowanceToSubtract > getLeftAllowanceInDays(subject.getSubjectId())) {
      throw new ValidationException(
          messageSource.getMessage("error.validation.leavetoolong", null, Locale.getDefault()));
    }
    subject.getHrInformation().setUsedAllowance(newUsedAllowance);
    subjectService.updateSubjectHRInformation(subject.getSubjectId(), subject.getHrInformation());
    return subject;
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void revertSubtractedDaysForApplication(
      final Subject subject, final LeaveApplication leaveApplication) {
    final long allowanceSubtracted =
        holidayService.getWorkingDaysInBetween(
            leaveApplication.getStartDate(), leaveApplication.getEndDate());
    final long currentlyUsedAllowance = subject.getHrInformation().getUsedAllowance();
    final long newUsedAllowance = currentlyUsedAllowance + allowanceSubtracted;
    subject.getHrInformation().setUsedAllowance(newUsedAllowance);
    subjectService.updateSubjectHRInformation(subject.getSubjectId(), subject.getHrInformation());
  }

  @Override
  public void scheduleResetUsedAllowance(final Date date, final long numberOfDaysToCarryOver)
      throws SchedulerException {
    if (!schedulerService.jobScheduled(new TriggerKey("reset-allowance-job"))) {
      final Trigger trigger =
          TriggerBuilder.newTrigger()
              .withIdentity("reset-allowance-job", "allowance-group")
              .usingJobData("numberOfDaysToCarryOver", numberOfDaysToCarryOver)
              .startAt(date)
              .withSchedule(
                  SimpleScheduleBuilder.simpleSchedule()
                      .withIntervalInMilliseconds(getYearInMilis())
                      .repeatForever())
              .build();
      final JobDetail jobDetail =
          JobBuilder.newJob(ResetAllowanceJob.class)
              .withIdentity("reset-allowance-job", "allowance-group")
              .build();
      schedulerService.schedule(jobDetail, trigger);
      schedulerService.start();
    }
  }

  @Override
  public void cancelResetUsedAllowance() throws SchedulerException {
    final JobKey jobKey = new JobKey("reset-allowance-job");
    final TriggerKey triggerKey = new TriggerKey("reset-allowance-job");
    schedulerService.unschedule(triggerKey);
    schedulerService.cancel(jobKey);
    schedulerService.stop();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void resetUsedAllowance(final long numberOfDaysToCarryOver) {
    allowanceRepository.resetAllowance(numberOfDaysToCarryOver);
  }

  private long getYearInMilis() {
    final LocalDate localDate = LocalDate.now(ZoneOffset.UTC);
    return localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
  }
}
