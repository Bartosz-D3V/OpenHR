package org.openhr.service.leaveapplication;

import org.openhr.dao.leaveapplication.LeaveApplicationDAO;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

  private final LeaveApplicationDAO leaveApplicationDAO;

  public LeaveApplicationServiceImpl(final LeaveApplicationDAO leaveApplicationDAO) {
    this.leaveApplicationDAO = leaveApplicationDAO;
  }

  @Override
  public LeaveApplication getLeaveApplication(final long applicationId) throws ApplicationDoesNotExistException {
    return leaveApplicationDAO.getLeaveApplication(applicationId);
  }

  @Override
  public LeaveApplication createLeaveApplication(final Subject subject, final LeaveApplication leaveApplication) {
    return leaveApplicationDAO.createLeaveApplication(subject, leaveApplication);
  }

  @Override
  public LeaveApplication updateLeaveApplication(final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException {
    return leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  public void rejectLeaveApplication(final Role role, final long applicationId)
    throws ApplicationDoesNotExistException {
    LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    leaveApplication = rejectApplication(role, leaveApplication);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  public void approveLeaveApplication(final Role role, final long applicationId)
    throws ApplicationDoesNotExistException {
    LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    leaveApplication = approveLeaveApplication(role, leaveApplication);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  public LeaveApplication rejectApplication(final Role role, final LeaveApplication leaveApplication) {
    switch (role) {
      case MANAGER:
        leaveApplication.setApprovedByManager(false);
      case HRTEAMMEMBER:
        leaveApplication.setApprovedByHR(false);
    }
    return leaveApplication;
  }

  public LeaveApplication approveLeaveApplication(final Role role, final LeaveApplication leaveApplication) {
    switch (role) {
      case MANAGER:
        leaveApplication.setApprovedByManager(true);
      case HRTEAMMEMBER:
        leaveApplication.setApprovedByHR(true);
    }
    return leaveApplication;
  }
}
