package org.openhr.facade.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.openhr.exception.SubjectDoesNotExistException;

public interface LeaveApplicationFacade {

  void createLeaveApplication(long subjectId, LeaveApplication leaveApplication) throws SubjectDoesNotExistException;

  void rejectLeaveApplication(Role role, long applicationId);

  void approveLeaveApplication(Role role, long applicationId);

}
