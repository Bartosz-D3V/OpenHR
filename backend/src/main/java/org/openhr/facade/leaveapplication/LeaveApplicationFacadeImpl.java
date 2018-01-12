package org.openhr.facade.leaveapplication;

import org.openhr.command.leaveapplicaion.LeaveApplicationCommand;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.process.TaskDefinition;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.service.leaveapplication.LeaveApplicationService;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeaveApplicationFacadeImpl implements LeaveApplicationFacade {

  private final LeaveApplicationService leaveApplicationService;
  private final PersonalDetailsService personalDetailsService;
  private final LeaveApplicationCommand leaveApplicationCommand;

  public LeaveApplicationFacadeImpl(final LeaveApplicationService leaveApplicationService,
                                    final PersonalDetailsService personalDetailsService,
                                    final LeaveApplicationCommand leaveApplicationCommand) {
    this.leaveApplicationService = leaveApplicationService;
    this.personalDetailsService = personalDetailsService;
    this.leaveApplicationCommand = leaveApplicationCommand;
  }

  @Override
  public LeaveApplication getLeaveApplication(final long applicationId) throws ApplicationDoesNotExistException {
    return leaveApplicationService.getLeaveApplication(applicationId);
  }

  @Override
  public LeaveApplication createLeaveApplication(final long subjectId, final LeaveApplication leaveApplication)
    throws SubjectDoesNotExistException, ApplicationDoesNotExistException {
    final Subject subject = personalDetailsService.getSubjectDetails(subjectId);
    final LeaveApplication savedLeaveApplication = leaveApplicationService.createLeaveApplication(subject,
      leaveApplication);
    final String processInstanceId = leaveApplicationCommand.startLeaveApplicationProcess(savedLeaveApplication);
    savedLeaveApplication.setProcessInstanceId(processInstanceId);

    return leaveApplicationService.updateLeaveApplication(savedLeaveApplication);
  }

  @Override
  public LeaveApplication updateLeaveApplication(final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException {
    return leaveApplicationService.updateLeaveApplication(leaveApplication);
  }

  @Override
  public void rejectLeaveApplication(final Role role, final String processInstanceId) {
    leaveApplicationCommand.rejectLeaveApplication(role, processInstanceId);
  }

  @Override
  public void approveLeaveApplication(final Role role, final String processInstanceId) {
    leaveApplicationCommand.approveLeaveApplication(role, processInstanceId);
  }

  @Override
  public final List<TaskDefinition> getProcessTasks(final String processInstanceId) {
    return leaveApplicationCommand.getProcessTasks(processInstanceId);
  }

  @Override
  public final List<String> getActiveProcessesId() {
    return leaveApplicationCommand.getActiveProcessesId();
  }
}
