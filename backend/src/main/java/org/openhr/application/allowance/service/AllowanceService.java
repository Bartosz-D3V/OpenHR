package org.openhr.application.allowance.service;

import java.util.Date;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ValidationException;
import org.quartz.SchedulerException;

public interface AllowanceService {
  long getLeftAllowanceInDays(long subjectId);

  long getAllowance(long subjectId);

  long getUsedAllowance(long subjectId);

  Subject subtractDaysFromSubjectAllowanceExcludingFreeDays(
      Subject subject, LeaveApplication leaveApplication) throws ValidationException;

  void revertSubtractedDaysForApplication(Subject subject, LeaveApplication leaveApplication);

  void scheduleResetUsedAllowance(Date date, long numberOfDaysToCarryOver)
      throws SchedulerException;

  void cancelResetUsedAllowance() throws SchedulerException;

  void resetUsedAllowance(long numberOfDaysToCarryOver);
}
