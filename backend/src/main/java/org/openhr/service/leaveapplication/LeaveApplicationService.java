package org.openhr.service.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;

public interface LeaveApplicationService {

  void createLeaveApplication(Subject subject, LeaveApplication leaveApplication);

  void rejectLeaveApplication(Role role, long applicationId);

  void approveLeaveApplication(Role role, long applicationId);

}
