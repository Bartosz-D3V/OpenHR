package org.openhr.service.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;

public interface LeaveApplicationService {

  LeaveApplication getLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  LeaveApplication createLeaveApplication(Subject subject, LeaveApplication leaveApplication);

  LeaveApplication updateLeaveApplication(LeaveApplication leaveApplication) throws ApplicationDoesNotExistException;

  void rejectLeaveApplication(Role role, long applicationId) throws ApplicationDoesNotExistException;

  void approveLeaveApplication(Role role, long applicationId) throws ApplicationDoesNotExistException;

}
