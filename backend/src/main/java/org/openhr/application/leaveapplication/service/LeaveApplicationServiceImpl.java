package org.openhr.application.leaveapplication.service;

import org.openhr.application.leaveapplication.dao.LeaveApplicationDAO;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.subject.Subject;
import org.openhr.application.leaveapplication.enumeration.Role;
import org.openhr.common.exception.ApplicationDoesNotExistException;
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
    leaveApplication = rejectLeaveApplication(role, leaveApplication);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  public void approveLeaveApplication(final Role role, final long applicationId)
    throws ApplicationDoesNotExistException {
    LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    leaveApplication = approveLeaveApplication(role, leaveApplication);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  LeaveApplication rejectLeaveApplication(final Role role, final LeaveApplication leaveApplication) {
    switch (role) {
      case MANAGER:
        leaveApplication.setApprovedByManager(false);
      case HRTEAMMEMBER:
        leaveApplication.setApprovedByHR(false);
    }
    return leaveApplication;
  }

  LeaveApplication approveLeaveApplication(final Role role, final LeaveApplication leaveApplication) {
    switch (role) {
      case MANAGER:
        leaveApplication.setApprovedByManager(true);
      case HRTEAMMEMBER:
        leaveApplication.setApprovedByHR(true);
    }
    return leaveApplication;
  }
}
