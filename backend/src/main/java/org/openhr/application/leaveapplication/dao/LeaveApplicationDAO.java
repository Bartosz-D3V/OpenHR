package org.openhr.application.leaveapplication.dao;

import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;

public interface LeaveApplicationDAO {

  LeaveApplication getLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  LeaveApplication createLeaveApplication(Subject subject, LeaveApplication leaveApplication);

  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication) throws ApplicationDoesNotExistException;

  long getLeaveApplicationIdByProcessId(String processInstanceId);

  Subject getApplicationApplicant(long applicationId);
}
