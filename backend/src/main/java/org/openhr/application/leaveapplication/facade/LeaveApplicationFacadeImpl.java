package org.openhr.application.leaveapplication.facade;

import org.openhr.application.leaveapplication.command.LeaveApplicationCommand;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.service.LeaveApplicationService;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.process.TaskDefinition;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    throws SubjectDoesNotExistException, ValidationException, ApplicationDoesNotExistException {
    final Subject subject = subjectService.getSubjectDetails(subjectId);
    final LeaveApplication savedLeaveApplication = leaveApplicationService.createLeaveApplication(subject,
      leaveApplication);
    final String processInstanceId = leaveApplicationCommand.startLeaveApplicationProcess(subject.getRole(),
      savedLeaveApplication);
    savedLeaveApplication.setProcessInstanceId(processInstanceId);

    return leaveApplicationService.updateLeaveApplication(savedLeaveApplication);
  }

  @Override
  public LeaveApplication updateLeaveApplication(final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException {
    return leaveApplicationService.updateLeaveApplication(leaveApplication);
  }

  @Override
  public void rejectLeaveApplicationByManager(final String processInstanceId) {
    leaveApplicationCommand.rejectLeaveApplicationByManager(processInstanceId);
  }

  @Override
  public void approveLeaveApplicationByManager(final String processInstanceId) {
    leaveApplicationCommand.approveLeaveApplicationByManager(processInstanceId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveApplication> getAwaitingForManagerLeaveApplications(final long managerId) {
    return leaveApplicationService.getAwaitingForManagerLeaveApplications(managerId);
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
