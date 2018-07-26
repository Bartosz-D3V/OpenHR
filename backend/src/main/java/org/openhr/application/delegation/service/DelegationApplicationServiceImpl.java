package org.openhr.application.delegation.service;

import java.util.List;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.application.delegation.repository.DelegationApplicationRepository;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.service.EmployeeService;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.country.Country;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DelegationApplicationServiceImpl implements DelegationApplicationService {
  private final DelegationApplicationRepository delegationApplicationRepository;
  private final EmployeeService employeeService;
  private final SubjectService subjectService;

  public DelegationApplicationServiceImpl(
      final DelegationApplicationRepository delegationApplicationRepository,
      final EmployeeService employeeService,
      final SubjectService subjectService) {
    this.delegationApplicationRepository = delegationApplicationRepository;
    this.employeeService = employeeService;
    this.subjectService = subjectService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Country> getCountries() {
    return delegationApplicationRepository.getCountries();
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication createDelegationApplication(
      final Subject subject, final DelegationApplication delegationApplication)
      throws SubjectDoesNotExistException {
    delegationApplication.setSubject(subject);
    delegationApplication.setAssignee(subjectService.getSubjectSupervisor(subject.getSubjectId()));
    return delegationApplicationRepository.createDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public DelegationApplication getDelegationApplication(final long delegationApplicationId) {
    return delegationApplicationRepository.getDelegationApplication(delegationApplicationId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication updateDelegationApplication(
      final DelegationApplication delegationApplication) {
    return delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication saveProcessInstanceId(
      final DelegationApplication delegationApplication,
      final ExecutionEntityImpl delegateExecution) {
    delegationApplication.setProcessInstanceId(delegateExecution.getProcessInstanceId());
    return delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<DelegationApplication> getSubjectsDelegationApplications(final long subjectId) {
    return delegationApplicationRepository.getSubjectsDelegationApplications(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<DelegationApplication> getAwaitingForActionDelegationApplications(
      final long subjectId) {
    return delegationApplicationRepository.getAwaitingForActionDelegationApplications(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void assignToSupervisor(final DelegationApplication delegationApplication)
      throws SubjectDoesNotExistException {
    final Subject supervisor =
        subjectService.getSubjectSupervisor(delegationApplication.getSubject().getSubjectId());
    delegationApplication.setAssignee(supervisor);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void assignToApplicant(final DelegationApplication delegationApplication) {
    final Subject applicant = delegationApplication.getSubject();
    delegationApplication.setAssignee(applicant);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void assignToManager(final DelegationApplication delegationApplication) {
    final Subject applicant = delegationApplication.getSubject();
    final Employee employee = employeeService.getEmployee(applicant.getSubjectId());
    final Manager manager = employee.getManager();
    delegationApplication.setAssignee(manager);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void assignToHr(final DelegationApplication delegationApplication) {
    final Subject applicant = delegationApplication.getSubject();
    final Employee employee = employeeService.getEmployee(applicant.getSubjectId());
    final Manager manager = employee.getManager();
    final HrTeamMember hrTeamMember = manager.getHrTeamMember();
    delegationApplication.setAssignee(hrTeamMember);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void approveByManager(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByManager(true);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void rejectByManager(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByManager(false);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void approveByHr(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByHR(true);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void rejectByHr(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByHR(false);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void terminateDelegationApplication(final DelegationApplication delegationApplication) {
    delegationApplication.setTerminated(true);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void resumeDelegationApplication(final DelegationApplication delegationApplication) {
    delegationApplication.setTerminated(false);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }
}
