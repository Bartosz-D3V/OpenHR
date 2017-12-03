package org.openhr.service.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;

public interface LeaveApplicationService {

  void createLeaveApplication(Role role, LeaveApplication leaveApplication);

  void rejectLeaveApplication(Role role, long userId, long applicationId);

  void approveLeaveApplication(Role role, long userId, long applicationId);

}
