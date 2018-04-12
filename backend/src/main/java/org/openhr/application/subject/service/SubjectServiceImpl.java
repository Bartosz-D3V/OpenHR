package org.openhr.application.subject.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.holiday.service.HolidayService;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.application.subject.repository.SubjectRepository;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.openhr.common.proxy.worker.WorkerProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectServiceImpl implements SubjectService {
  private final SubjectRepository subjectRepository;
  private final WorkerProxy workerProxy;
  private final HolidayService holidayService;

  public SubjectServiceImpl(
      final SubjectRepository subjectRepository,
      final WorkerProxy workerProxy,
      final HolidayService holidayService) {
    this.subjectRepository = subjectRepository;
    this.workerProxy = workerProxy;
    this.holidayService = holidayService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Subject> getSubjects() {
    return subjectRepository.getSubjects();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return subjectRepository.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Subject updateSubject(final long subjectId, final Subject subject)
      throws SubjectDoesNotExistException {
    return subjectRepository.updateSubject(subjectId, subject);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectPersonalInformation(
      final long subjectId, final PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectRepository.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectContactInformation(
      final long subjectId, final ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectRepository.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectEmployeeInformation(
      final long subjectId, final EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectRepository.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteSubject(final long subjectId)
      throws HibernateException, SubjectDoesNotExistException {
    subjectRepository.deleteSubject(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LightweightSubjectDTO getLightweightSubject(final long subjectId)
      throws SubjectDoesNotExistException {
    return subjectRepository.getLightweightSubject(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getLeftAllowanceInDays(final long subjectId) {
    return subjectRepository.getAllowance(subjectId)
        - subjectRepository.getUsedAllowance(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getUsedAllowance(final long subjectId) {
    return subjectRepository.getUsedAllowance(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void subtractDaysFromSubjectAllowanceExcludingFreeDays(
      final Subject subject, final LeaveApplication leaveApplication) throws ValidationException {
    final long allowanceToSubtract =
        holidayService.getWorkingDaysBetweenIncl(
            leaveApplication.getStartDate(), leaveApplication.getEndDate());
    final long newUsedAllowance = getUsedAllowance(subject.getSubjectId()) + allowanceToSubtract;
    if (newUsedAllowance > getLeftAllowanceInDays(subject.getSubjectId())) {
      throw new ValidationException("Not enough leave allowance");
    }
    if (allowanceToSubtract > getLeftAllowanceInDays(subject.getSubjectId())) {
      throw new ValidationException("Leave is too long");
    }
    subject.getHrInformation().setUsedAllowance(newUsedAllowance);
    subjectRepository.updateSubjectHRInformation(
        subject.getSubjectId(), subject.getHrInformation());
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void revertSubtractedDaysForApplication(
      final Subject subject, final LeaveApplication leaveApplication) {
    final long allowanceSubtracted =
        holidayService.getWorkingDaysBetweenIncl(
            leaveApplication.getStartDate(), leaveApplication.getEndDate());
    final long currentlyUsedAllowance = subject.getHrInformation().getUsedAllowance();
    final long newUsedAllowance = currentlyUsedAllowance + allowanceSubtracted;
    subject.getHrInformation().setUsedAllowance(newUsedAllowance);
    subjectRepository.updateSubjectHRInformation(
        subject.getSubjectId(), subject.getHrInformation());
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Role getSubjectRole(final long subjectId) throws SubjectDoesNotExistException {
    return getSubjectDetails(subjectId).getRole();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectSupervisor(final long subjectId) throws SubjectDoesNotExistException {
    final Role subjectRole = getSubjectRole(subjectId);
    switch (subjectRole) {
      case EMPLOYEE:
        return workerProxy.getEmployee(subjectId).getManager();
      case MANAGER:
        return workerProxy.getManager(subjectId).getHrTeamMember();
      case HRTEAMMEMBER:
        return workerProxy.getHrTeamMember(subjectId);
      default:
        return null;
    }
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void setSubjectSupervisor(final long subjectId, final long supervisorId)
      throws SubjectDoesNotExistException, ValidationException {
    final Subject subject = getSubjectDetails(subjectId);
    final Subject supervisor = getSubjectDetails(supervisorId);
    if (relationshipIsValid(subject, supervisor)) {
      if (subject.getRole() == Role.EMPLOYEE && supervisor.getRole() == Role.MANAGER) {
        final Employee employee = workerProxy.getEmployee(subjectId);
        final Manager manager = workerProxy.getManager(supervisorId);
        employee.setManager(manager);
        manager.addEmployee(employee);
        subjectRepository.updateSubject(employee.getSubjectId(), employee);
        subjectRepository.updateSubject(manager.getSubjectId(), manager);
      } else if (subject.getRole() == Role.MANAGER && supervisor.getRole() == Role.HRTEAMMEMBER) {
        final Manager manager = workerProxy.getManager(subjectId);
        final HrTeamMember hrTeamMember = workerProxy.getHrTeamMember(supervisorId);
        manager.setHrTeamMember(hrTeamMember);
        hrTeamMember.addManager(manager);
        subjectRepository.updateSubject(manager.getSubjectId(), manager);
        subjectRepository.updateSubject(hrTeamMember.getSubjectId(), hrTeamMember);
      }
    }
  }

  private boolean relationshipIsValid(final Subject subject, final Subject supervisor)
      throws ValidationException {
    if (subject.getSubjectId() == supervisor.getSubjectId()) {
      throw new ValidationException("Cannot assign yourself as a supervisor");
    }
    if (subject.getRole() == Role.EMPLOYEE && supervisor.getRole() == Role.HRTEAMMEMBER) {
      throw new ValidationException("Employee cannot be assigned to HR");
    }
    if (subject.getRole() == Role.MANAGER && supervisor.getRole() == Role.MANAGER) {
      throw new ValidationException("Manager cannot be assigned to Manager");
    }
    if (subject.getRole() == Role.HRTEAMMEMBER && supervisor.getRole() == Role.HRTEAMMEMBER) {
      throw new ValidationException("HR cannot be assigned to HR");
    }
    return true;
  }
}
