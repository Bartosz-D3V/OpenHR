package org.openhr.dao.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;

public interface LeaveApplicationDAO {

  void createLeaveApplication(LeaveApplication leaveApplication);

  void rejectLeaveApplication(Role role, long applicationId);

  void approveLeaveApplication(Role role, long applicationId);

}
