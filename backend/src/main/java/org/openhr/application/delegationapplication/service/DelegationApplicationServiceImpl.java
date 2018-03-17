package org.openhr.application.delegationapplication.service;

import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.application.delegationapplication.repository.DelegationApplicationRepository;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.service.EmployeeService;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.manager.service.ManagerService;
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
  private final ManagerService managerService;

  public DelegationApplicationServiceImpl(final DelegationApplicationRepository delegationApplicationRepository,
                                          final EmployeeService employeeService,
                                          final ManagerService managerService) {
    this.delegationApplicationRepository = delegationApplicationRepository;
    this.employeeService = employeeService;
    this.managerService = managerService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Country> getCountries() {
    return delegationApplicationRepository.getCountries();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public DelegationApplication createDelegationApplication(final Subject subject,
                                                           final DelegationApplication delegationApplication) {
    delegationApplication.setSubject(subject);
    return delegationApplicationRepository.createDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public DelegationApplication saveProcessInstanceId(final String processInstanceId,
                                                     final DelegationApplication delegationApplication) {
    delegationApplication.setProcessInstanceId(processInstanceId);
    return delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  public void assignToApplicant(final DelegationApplication delegationApplication) {
    final Subject applicant = delegationApplication.getSubject();
    delegationApplication.setAssignee(applicant);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  public void assignToManager(final DelegationApplication delegationApplication) {
    final Subject applicant = delegationApplication.getSubject();
    final Employee employee = employeeService.getEmployee(applicant.getSubjectId());
    final Manager manager = employee.getManager();
    delegationApplication.setAssignee(manager);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void assignToHr(final DelegationApplication delegationApplication) {
    final Subject applicant = delegationApplication.getSubject();
    final Employee employee = employeeService.getEmployee(applicant.getSubjectId());
    final Manager manager = employee.getManager();
    final HrTeamMember hrTeamMember = manager.getHrTeamMember();
    delegationApplication.setAssignee(hrTeamMember);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void approveByManager(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByManager(true);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void rejectByManager(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByManager(false);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void approveByHr(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByHR(true);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void rejectByHr(final DelegationApplication delegationApplication) {
    delegationApplication.setApprovedByHR(false);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void terminateDelegationApplication(final DelegationApplication delegationApplication) {
    delegationApplication.setTerminated(true);
    delegationApplicationRepository.updateDelegationApplication(delegationApplication);
  }
}
