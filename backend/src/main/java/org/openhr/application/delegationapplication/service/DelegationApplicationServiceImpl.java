package org.openhr.application.delegationapplication.service;

import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.application.delegationapplication.repository.DelegationApplicationRepository;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.service.EmployeeService;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.domain.country.Country;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DelegationApplicationServiceImpl implements DelegationApplicationService {
  private final DelegationApplicationRepository delegationApplicationRepository;
  private final EmployeeService employeeService;

  public DelegationApplicationServiceImpl(final DelegationApplicationRepository delegationApplicationRepository,
                                          final EmployeeService employeeService) {
    this.delegationApplicationRepository = delegationApplicationRepository;
    this.employeeService = employeeService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Country> getCountries() {
    return delegationApplicationRepository.getCountries();
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication createDelegationApplication(final Subject subject,
                                                           final DelegationApplication delegationApplication) {
    delegationApplication.setSubject(subject);
    return delegationApplicationRepository.createDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication updateDelegationApplication(final DelegationApplication delegationApplication) {
    return delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication saveProcessInstanceId(final DelegationApplication delegationApplication,
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
  public List<DelegationApplication> getAwaitingForActionDelegationApplications(final long subjectId) {
    return delegationApplicationRepository.getAwaitingForActionDelegationApplications(subjectId);
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
}
