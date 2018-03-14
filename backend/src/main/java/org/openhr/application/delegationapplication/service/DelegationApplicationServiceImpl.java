package org.openhr.application.delegationapplication.service;

import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.application.delegationapplication.repository.DelegationApplicationRepository;
import org.openhr.common.domain.country.Country;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DelegationApplicationServiceImpl implements DelegationApplicationService {
  private final DelegationApplicationRepository delegationApplicationRepository;

  public DelegationApplicationServiceImpl(final DelegationApplicationRepository delegationApplicationRepository) {
    this.delegationApplicationRepository = delegationApplicationRepository;
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
  @Transactional(propagation = Propagation.REQUIRED)
  public void assignToApplicant(final DelegationApplication delegationApplication) {
    final Subject applicant = delegationApplication.getSubject();
    delegationApplication.setAssignee(applicant);
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
}
