package org.openhr.service.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.springframework.stereotype.Service;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

  @Override
  public void createLeaveApplication(Role role, LeaveApplication leaveApplication) {

  }

  @Override
  public void rejectLeaveApplication(Role role, long userId, long applicationId) {

  }

  @Override
  public void approveLeaveApplication(Role role, long userId, long applicationId) {

  }

}
