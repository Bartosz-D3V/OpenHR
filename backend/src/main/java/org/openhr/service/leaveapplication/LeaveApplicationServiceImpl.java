package org.openhr.service.leaveapplication;

import org.openhr.dao.leaveapplication.LeaveApplicationDAO;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.springframework.stereotype.Service;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

  private final LeaveApplicationDAO leaveApplicationDAO;

  public LeaveApplicationServiceImpl(final LeaveApplicationDAO leaveApplicationDAO) {
    this.leaveApplicationDAO = leaveApplicationDAO;
  }

  @Override
  public void createLeaveApplication(final Subject subject, final LeaveApplication leaveApplication) {
    leaveApplicationDAO.createLeaveApplication(subject, leaveApplication);
  }

  @Override
  public void rejectLeaveApplication(final Role role, final long applicationId) {
    leaveApplicationDAO.rejectLeaveApplication(role, applicationId);
  }

  @Override
  public void approveLeaveApplication(final Role role, final long applicationId) {
    leaveApplicationDAO.approveLeaveApplication(role, applicationId);
  }

}
