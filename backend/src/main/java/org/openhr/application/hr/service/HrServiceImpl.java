package org.openhr.application.hr.service;

import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.hr.repository.HrRepository;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.proxy.worker.WorkerProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HrServiceImpl implements HrService {
  private final UserService userService;
  private final HrRepository hrRepository;
  private final WorkerProxy workerProxy;

  public HrServiceImpl(
      final UserService userService,
      final HrRepository hrRepository,
      final WorkerProxy workerProxy) {
    this.userService = userService;
    this.hrRepository = hrRepository;
    this.workerProxy = workerProxy;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return hrRepository.getHrTeamMember(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) {
    final User user = hrTeamMember.getUser();
    final String encodedPassword = userService.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);
    user.setUserRoles(userService.setHrUserRole(user));
    hrTeamMember.setRole(Role.HRTEAMMEMBER);

    return hrRepository.addHrTeamMember(hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    return hrRepository.updateHrTeamMember(subjectId, hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteHrTeamMember(final long subjectId) throws SubjectDoesNotExistException {
    hrRepository.deleteHrTeamMember(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void addManagerToHr(final long hrTeamMemberId, final long managerId)
      throws SubjectDoesNotExistException {
    final HrTeamMember hrTeamMember = getHrTeamMember(hrTeamMemberId);
    final Manager manager = workerProxy.getManager(managerId);
    hrRepository.addManagerToHr(hrTeamMember, manager);
  }
}
