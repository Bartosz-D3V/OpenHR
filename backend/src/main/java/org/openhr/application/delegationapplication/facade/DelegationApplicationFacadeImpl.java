package org.openhr.application.delegationapplication.facade;

import org.openhr.application.delegationapplication.command.DelegationApplicationCommand;
import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.application.delegationapplication.service.DelegationApplicationService;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.country.Country;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DelegationApplicationFacadeImpl implements DelegationApplicationFacade {
  private final DelegationApplicationService delegationApplicationService;
  private final DelegationApplicationCommand delegationApplicationCommand;
  private final SubjectService subjectService;

  public DelegationApplicationFacadeImpl(final DelegationApplicationService delegationApplicationService,
                                         final DelegationApplicationCommand delegationApplicationCommand,
                                         final SubjectService subjectService) {
    this.delegationApplicationService = delegationApplicationService;
    this.delegationApplicationCommand = delegationApplicationCommand;
    this.subjectService = subjectService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Country> getCountries() {
    return delegationApplicationService.getCountries();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public DelegationApplication startDelegationApplicationProcess(final long subjectId,
                                                                 final DelegationApplication delegationApplication)
    throws SubjectDoesNotExistException {
    final Subject subject = subjectService.getSubjectDetails(subjectId);
    DelegationApplication savedDelegationApplication = delegationApplicationService
      .createDelegationApplication(subject, delegationApplication);
    final String processInstanceId = delegationApplicationCommand
      .startDelegationProcess(subject, savedDelegationApplication);
    savedDelegationApplication = delegationApplicationService
      .saveProcessInstanceId(processInstanceId, savedDelegationApplication);
    return savedDelegationApplication;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void rejectDelegationApplicationByManager(final String processInstanceId) {
    delegationApplicationCommand.rejectByManager(processInstanceId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void approveDelegationApplicationByManager(final String processInstanceId) {
    delegationApplicationCommand.approveByHr(processInstanceId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void rejectDelegationApplicationByHr(final String processInstanceId) {
    delegationApplicationCommand.rejectByHr(processInstanceId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void approveDelegationApplicationByHr(final String processInstanceId) {
    delegationApplicationCommand.approveByHr(processInstanceId);
  }
}
