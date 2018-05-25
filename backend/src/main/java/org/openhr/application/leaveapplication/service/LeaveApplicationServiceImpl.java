package org.openhr.application.leaveapplication.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import org.openhr.application.allowance.service.AllowanceService;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.openhr.common.util.iterable.LocalDateRange;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

  private final LeaveApplicationRepository leaveApplicationRepository;
  private final SubjectService subjectService;
  private final AllowanceService allowanceService;
  private final MessageSource messageSource;

  public LeaveApplicationServiceImpl(
      final LeaveApplicationRepository leaveApplicationRepository,
      final SubjectService subjectService,
      final AllowanceService allowanceService,
      final MessageSource messageSource) {
    this.leaveApplicationRepository = leaveApplicationRepository;
    this.subjectService = subjectService;
    this.allowanceService = allowanceService;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LeaveApplication getLeaveApplication(final long leaveApplicationId)
      throws ApplicationDoesNotExistException {
    return leaveApplicationRepository.getLeaveApplication(leaveApplicationId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveApplication> getSubjectsLeaveApplications(final long subjectId) {
    return leaveApplicationRepository.getSubjectsLeaveApplications(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public LeaveApplication createLeaveApplication(
      final Subject subject, final LeaveApplication leaveApplication) throws ValidationException {
    validateLeaveApplication(leaveApplication);
    validateLeftAllowance(subject);
    validateBookedApplications(leaveApplication, subject.getSubjectId());
    final long leaveTypeId = leaveApplication.getLeaveType().getLeaveTypeId();
    leaveApplication.setLeaveType(getLeaveTypeById(leaveTypeId));
    allowanceService.subtractDaysFromSubjectAllowanceExcludingFreeDays(subject, leaveApplication);

    return leaveApplicationRepository.createLeaveApplication(subject, leaveApplication);
  }

  private void validateLeaveApplication(final LeaveApplication leaveApplication)
      throws ValidationException {
    final LocalDate startDate = leaveApplication.getStartDate();
    final LocalDate endDate = leaveApplication.getEndDate();
    if (startDate.isAfter(endDate)) {
      throw new ValidationException(
          messageSource.getMessage(
              "error.validation.daterangenotvalid", null, Locale.getDefault()));
    }
  }

  private void validateLeftAllowance(final Subject subject) throws ValidationException {
    if (allowanceService.getLeftAllowanceInDays(subject.getSubjectId()) == 0) {
      throw new ValidationException(
          messageSource.getMessage("error.validation.noleftallowance", null, Locale.getDefault()));
    }
  }

  private void validateBookedApplications(
      final LeaveApplication leaveApplication, final long subjectId) throws ValidationException {
    final LocalDateRange dateRange =
        new LocalDateRange(leaveApplication.getStartDate(), leaveApplication.getEndDate());
    if (!getSubjectsLeaveApplicationsInRange(dateRange, subjectId).isEmpty()) {
      throw new ValidationException(
          messageSource.getMessage(
              "error.validation.selectedrangealreadybooked", null, Locale.getDefault()));
    }
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public LeaveApplication updateLeaveApplication(
      final long leaveApplicationId, final LeaveApplication leaveApplication)
      throws ApplicationDoesNotExistException {
    return leaveApplicationRepository.updateLeaveApplication(leaveApplicationId, leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void rejectLeaveApplicationByManager(final long applicationId)
      throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication =
        leaveApplicationRepository.getLeaveApplication(applicationId);
    final Subject subject = getApplicationApplicant(applicationId);
    leaveApplication.setApprovedByManager(false);
    allowanceService.revertSubtractedDaysForApplication(subject, leaveApplication);
    leaveApplicationRepository.updateLeaveApplication(applicationId, leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void approveLeaveApplicationByManager(final long applicationId)
      throws ApplicationDoesNotExistException, ValidationException, SubjectDoesNotExistException {
    final LeaveApplication leaveApplication =
        leaveApplicationRepository.getLeaveApplication(applicationId);
    final Subject subject =
        subjectService.getSubjectDetails(leaveApplication.getSubject().getSubjectId());
    validateLeaveApplication(leaveApplication);
    validateLeftAllowance(subject);
    validateBookedApplications(leaveApplication, subject.getSubjectId());
    leaveApplication.setApprovedByManager(true);
    leaveApplicationRepository.updateLeaveApplication(applicationId, leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void rejectLeaveApplicationByHr(final long applicationId)
      throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication =
        leaveApplicationRepository.getLeaveApplication(applicationId);
    final Subject subject = getApplicationApplicant(applicationId);
    leaveApplication.setApprovedByHR(false);
    allowanceService.revertSubtractedDaysForApplication(subject, leaveApplication);
    leaveApplicationRepository.updateLeaveApplication(applicationId, leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void approveLeaveApplicationByHr(final long applicationId)
      throws ApplicationDoesNotExistException, SubjectDoesNotExistException, ValidationException {
    final LeaveApplication leaveApplication =
        leaveApplicationRepository.getLeaveApplication(applicationId);
    final Subject subject =
        subjectService.getSubjectDetails(leaveApplication.getSubject().getSubjectId());
    validateLeaveApplication(leaveApplication);
    validateLeftAllowance(subject);
    validateBookedApplications(leaveApplication, subject.getSubjectId());
    leaveApplication.setApprovedByHR(true);
    leaveApplicationRepository.updateLeaveApplication(applicationId, leaveApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void terminateLeaveApplication(long applicationId)
      throws ApplicationDoesNotExistException {
    final LeaveApplication leaveApplication =
        leaveApplicationRepository.getLeaveApplication(applicationId);
    leaveApplication.setTerminated(true);
    leaveApplicationRepository.updateLeaveApplication(applicationId, leaveApplication);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveApplication> getAwaitingForActionLeaveApplications(final long subjectId) {
    return leaveApplicationRepository.getAwaitingForActionLeaveApplications(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getApplicationApplicant(final long applicationId) {
    return leaveApplicationRepository.getApplicationApplicant(applicationId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getApplicationAssignee(final long applicationId) {
    return leaveApplicationRepository.getApplicationAssignee(applicationId);
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
    return leaveApplicationRepository.getLeaveApplicationIdByProcessId(processInstanceId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LeaveApplication> getSubjectsLeaveApplicationsInRange(
      final LocalDateRange dateRange, final long subjectId) {
    return leaveApplicationRepository.getLeaveApplicationsInRange(dateRange, subjectId);
  }
}
