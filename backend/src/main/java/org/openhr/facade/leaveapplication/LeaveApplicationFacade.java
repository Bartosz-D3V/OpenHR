package org.openhr.facade.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;

public interface LeaveApplicationFacade {

  LeaveApplication getLeaveApplication(long applicationId) throws ApplicationDoesNotExistException;

  void createLeaveApplication(long subjectId, LeaveApplication leaveApplication) throws SubjectDoesNotExistException;

  void updateLeaveApplication(LeaveApplication leaveApplication) throws ApplicationDoesNotExistException;

  void rejectLeaveApplication(Role role, long applicationId) throws ApplicationDoesNotExistException;

  void approveLeaveApplication(Role role, long applicationId) throws ApplicationDoesNotExistException;

}
