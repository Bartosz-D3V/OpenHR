package org.openhr.facade.leaveapplication;

import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.service.leaveapplication.LeaveApplicationService;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Component;

@Component
public class LeaveApplicationFacadeImpl implements LeaveApplicationFacade {

  private final LeaveApplicationService leaveApplicationService;
  private final PersonalDetailsService personalDetailsService;

  public LeaveApplicationFacadeImpl(final LeaveApplicationService leaveApplicationService,
                                    final PersonalDetailsService personalDetailsService) {
    this.leaveApplicationService = leaveApplicationService;
    this.personalDetailsService = personalDetailsService;
  }

  @Override
  public LeaveApplication getLeaveApplication(final long applicationId) throws ApplicationDoesNotExistException {
    return leaveApplicationService.getLeaveApplication(applicationId);
  }

  @Override
  public void createLeaveApplication(final long subjectId, final LeaveApplication leaveApplication)
    throws SubjectDoesNotExistException {
    final Subject subject = personalDetailsService.getSubjectDetails(subjectId);
    leaveApplicationService.createLeaveApplication(subject, leaveApplication);
  }

  @Override
  public void updateLeaveApplication(final LeaveApplication leaveApplication) throws ApplicationDoesNotExistException {
    leaveApplicationService.updateLeaveApplication(leaveApplication);
  }

  @Override
  public void rejectLeaveApplication(final Role role, final long applicationId)
    throws ApplicationDoesNotExistException {
    leaveApplicationService.rejectLeaveApplication(role, applicationId);
  }

  @Override
  public void approveLeaveApplication(final Role role, final long applicationId)
    throws ApplicationDoesNotExistException {
    leaveApplicationService.approveLeaveApplication(role, applicationId);
  }
}
