package org.openhr.application.leaveapplication.facade;

import java.util.List;
import org.openhr.application.leaveapplication.command.LeaveApplicationCommand;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.service.LeaveApplicationService;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeaveApplicationFacadeImpl implements LeaveApplicationFacade {

  private final LeaveApplicationService leaveApplicationService;
  private final SubjectService subjectService;
  private final LeaveApplicationCommand leaveApplicationCommand;

  public LeaveApplicationFacadeImpl(
      final LeaveApplicationService leaveApplicationService,
      final SubjectService subjectService,
      final LeaveApplicationCommand leaveApplicationCommand) {
    this.leaveApplicationService = leaveApplicationService;
    this.subjectService = subjectService;
    this.leaveApplicationCommand = leaveApplicationCommand;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveApplication getLeaveApplication(final long leaveApplicationId)
      throws ApplicationDoesNotExistException {
    return leaveApplicationService.getLeaveApplication(leaveApplicationId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveApplication> getSubjectsLeaveApplications(final long subjectId) {
    return leaveApplicationService.getSubjectsLeaveApplications(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public LeaveApplication createLeaveApplication(
      final long subjectId, final LeaveApplication leaveApplication)
      throws SubjectDoesNotExistException, ValidationException, ApplicationDoesNotExistException {
    final Subject subject = subjectService.getSubjectDetails(subjectId);
    leaveApplication.setAssignee(subjectService.getSubjectSupervisor(subjectId));
    final LeaveApplication savedLeaveApplication =
        leaveApplicationService.createLeaveApplication(subject, leaveApplication);
    final String processInstanceId =
        leaveApplicationCommand.startLeaveApplicationProcess(subject, savedLeaveApplication);
    savedLeaveApplication.setProcessInstanceId(processInstanceId);

    return leaveApplicationService.updateLeaveApplication(
        savedLeaveApplication.getApplicationId(), savedLeaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public LeaveApplication updateLeaveApplication(
      final long leaveApplicationId, final LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException {
    return leaveApplicationService.updateLeaveApplication(leaveApplicationId, leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void rejectLeaveApplicationByManager(
      final String processInstanceId, final String refusalReason)
      throws ApplicationDoesNotExistException {
    final long applicationId =
        leaveApplicationService.getLeaveApplicationIdByProcessId(processInstanceId);
    final LeaveApplication leaveApplication =
        leaveApplicationService.getLeaveApplication(applicationId);
    leaveApplication.setAssignee(null);
    leaveApplication.setRefusalReason(refusalReason);
    leaveApplicationCommand.rejectLeaveApplicationByManager(processInstanceId, applicationId);
    leaveApplicationService.updateLeaveApplication(
        leaveApplication.getApplicationId(), leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void approveLeaveApplicationByManager(final String processInstanceId)
      throws ApplicationDoesNotExistException, SubjectDoesNotExistException {
    final long applicationId =
        leaveApplicationService.getLeaveApplicationIdByProcessId(processInstanceId);
    final Subject currentAssignee = leaveApplicationService.getApplicationAssignee(applicationId);
    final Subject supervisor = subjectService.getSubjectSupervisor(currentAssignee.getSubjectId());
    leaveApplicationCommand.approveLeaveApplicationByManager(processInstanceId, applicationId);
    final LeaveApplication leaveApplication =
        leaveApplicationService.getLeaveApplication(applicationId);
    leaveApplication.setAssignee(supervisor);
    leaveApplicationService.updateLeaveApplication(
        leaveApplication.getApplicationId(), leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void rejectLeaveApplicationByHR(final String processInstanceId, final String refusalReason)
      throws ApplicationDoesNotExistException {
    final long applicationId =
        leaveApplicationService.getLeaveApplicationIdByProcessId(processInstanceId);
    final LeaveApplication leaveApplication =
        leaveApplicationService.getLeaveApplication(applicationId);
    leaveApplication.setAssignee(null);
    leaveApplication.setRefusalReason(refusalReason);
    leaveApplicationCommand.rejectLeaveApplicationByHr(processInstanceId);
    leaveApplicationService.updateLeaveApplication(
        leaveApplication.getApplicationId(), leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void approveLeaveApplicationByHR(final String processInstanceId)
      throws ApplicationDoesNotExistException {
    final long applicationId =
        leaveApplicationService.getLeaveApplicationIdByProcessId(processInstanceId);
    final LeaveApplication leaveApplication =
        leaveApplicationService.getLeaveApplication(applicationId);
    leaveApplication.setAssignee(null);
    leaveApplicationCommand.approveLeaveApplicationByHr(processInstanceId);
    leaveApplicationService.updateLeaveApplication(
        leaveApplication.getApplicationId(), leaveApplication);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveApplication> getAwaitingForActionLeaveApplications(final long subjectId) {
    return leaveApplicationService.getAwaitingForActionLeaveApplications(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveType> getLeaveTypes() {
    return leaveApplicationService.getLeaveTypes();
  }
}
