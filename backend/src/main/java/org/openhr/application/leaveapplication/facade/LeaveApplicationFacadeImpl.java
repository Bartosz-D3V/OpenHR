package org.openhr.application.leaveapplication.facade;

import org.openhr.application.leaveapplication.command.LeaveApplicationCommand;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.common.domain.subject.Subject;
import org.openhr.application.leaveapplication.enumeration.Role;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.application.leaveapplication.service.LeaveApplicationService;
import org.openhr.application.subject.service.SubjectService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LeaveApplicationFacadeImpl implements LeaveApplicationFacade {

  private final LeaveApplicationService leaveApplicationService;
  private final SubjectService subjectService;
  private final LeaveApplicationCommand leaveApplicationCommand;

  public LeaveApplicationFacadeImpl(final LeaveApplicationService leaveApplicationService,
                                    final SubjectService subjectService,
                                    final LeaveApplicationCommand leaveApplicationCommand) {
    this.leaveApplicationService = leaveApplicationService;
    this.subjectService = subjectService;
    this.leaveApplicationCommand = leaveApplicationCommand;
  }

  @Override
  public LeaveApplication getLeaveApplication(final long applicationId) throws ApplicationDoesNotExistException {
    return leaveApplicationService.getLeaveApplication(applicationId);
  }

  @Override
  public LeaveApplication createLeaveApplication(final long subjectId, final LeaveApplication leaveApplication)
    throws SubjectDoesNotExistException, ApplicationDoesNotExistException {
    final Subject subject = subjectService.getSubjectDetails(subjectId);
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

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveType> getLeaveTypes() {
    return leaveApplicationService.getLeaveTypes();
  }
}
