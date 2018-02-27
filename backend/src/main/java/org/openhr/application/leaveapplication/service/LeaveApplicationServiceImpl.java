package org.openhr.application.leaveapplication.service;

import org.openhr.application.leaveapplication.dao.LeaveApplicationDAO;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

  private final LeaveApplicationDAO leaveApplicationDAO;
  private final LeaveApplicationRepository leaveApplicationRepository;
  private final SubjectService subjectService;

  public LeaveApplicationServiceImpl(final LeaveApplicationDAO leaveApplicationDAO,
                                     final LeaveApplicationRepository leaveApplicationRepository,
                                     final SubjectService subjectService) {
    this.leaveApplicationDAO = leaveApplicationDAO;
    this.leaveApplicationRepository = leaveApplicationRepository;
    this.subjectService = subjectService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveApplication getLeaveApplication(final long applicationId) throws ApplicationDoesNotExistException {
    return leaveApplicationDAO.getLeaveApplication(applicationId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public LeaveApplication createLeaveApplication(final Subject subject, final LeaveApplication leaveApplication)
    throws ValidationException {
    validateLeaveApplication(leaveApplication);
    validateLeftAllowance(subject);
    final long leaveTypeId = leaveApplication.getLeaveType().getLeaveTypeId();
    leaveApplication.setLeaveType(getLeaveTypeById(leaveTypeId));
    subjectService.subtractDaysFromSubjectAllowanceExcludingFreeDays(subject, leaveApplication);

    return leaveApplicationDAO.createLeaveApplication(subject, leaveApplication);
  }

  private void validateLeaveApplication(final LeaveApplication leaveApplication) throws ValidationException {
    final LocalDate startDate = leaveApplication.getStartDate();
    final LocalDate endDate = leaveApplication.getEndDate();
    if (startDate.isAfter(endDate)) {
      throw new ValidationException("Provided dates are not valid");
    }
  }

  private void validateLeftAllowance(final Subject subject) throws ValidationException {
    if (subjectService.getLeftAllowanceInDays(subject.getSubjectId()) == 0) {
      throw new ValidationException("No leave allowance left");
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public LeaveApplication updateLeaveApplication(final LeaveApplication leaveApplication)
    throws ApplicationDoesNotExistException {
    return leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void rejectLeaveApplicationByManager(final long applicationId)
    throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    final Subject subject = getApplicationApplicant(applicationId);
    leaveApplication.setApprovedByManager(false);
    subjectService.revertSubtractedDaysForApplication(subject, leaveApplication);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void approveLeaveApplicationByManager(final long applicationId)
    throws ApplicationDoesNotExistException {
    LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    leaveApplication.setApprovedByManager(true);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void rejectLeaveApplicationByHr(final long applicationId)
    throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    final Subject subject = getApplicationApplicant(applicationId);
    leaveApplication.setApprovedByHR(false);
    subjectService.revertSubtractedDaysForApplication(subject, leaveApplication);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void approveLeaveApplicationByHr(final long applicationId)
    throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    leaveApplication.setApprovedByHR(true);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void terminateLeaveApplication(long applicationId) throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication = leaveApplicationDAO.getLeaveApplication(applicationId);
    leaveApplication.setTerminated(true);
    leaveApplicationDAO.updateLeaveApplication(leaveApplication);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveApplication> getAwaitingForActionLeaveApplications(final long subjectId) {
    return leaveApplicationRepository.getAwaitingForActionLeaveApplications(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getApplicationApplicant(final long applicationId) {
    return leaveApplicationDAO.getApplicationApplicant(applicationId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveType> getLeaveTypes() {
    return leaveApplicationRepository.getLeaveTypes();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveType getLeaveTypeById(final long leaveTypeId) {
    return leaveApplicationRepository.getLeaveType(leaveTypeId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getLeaveApplicationIdByProcessId(final String processInstanceId) {
    return leaveApplicationDAO.getLeaveApplicationIdByProcessId(processInstanceId);
  }
}
