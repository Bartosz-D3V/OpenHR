package org.openhr.facade.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.openhr.service.leaveapplication.LeaveApplicationService;
import org.springframework.stereotype.Component;

@Component
public class LeaveApplicationFacadeImpl implements LeaveApplicationFacade {

  private final LeaveApplicationService leaveApplicationService;

  public LeaveApplicationFacadeImpl(final LeaveApplicationService leaveApplicationService) {
    this.leaveApplicationService = leaveApplicationService;
  }

  @Override
  public void createLeaveApplication(final LeaveApplication leaveApplication) {
    leaveApplicationService.createLeaveApplication(leaveApplication);
  }

  @Override
  public void rejectLeaveApplication(final Role role, final long applicationId) {
    leaveApplicationService.rejectLeaveApplication(role, applicationId);
  }

  @Override
  public void approveLeaveApplication(final Role role, final long applicationId) {
    leaveApplicationService.approveLeaveApplication(role, applicationId);
  }
}
