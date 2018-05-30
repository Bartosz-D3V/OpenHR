package org.openhr.application.hr.service;

import java.util.List;
import java.util.Locale;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.hr.repository.HrRepository;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.proxy.worker.WorkerProxy;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HrServiceImpl implements HrService {
  private final UserService userService;
  private final HrRepository hrRepository;
  private final WorkerProxy workerProxy;
  private final MessageSource messageSource;

  public HrServiceImpl(
      final UserService userService,
      final HrRepository hrRepository,
      final WorkerProxy workerProxy,
      final MessageSource messageSource) {
    this.userService = userService;
    this.hrRepository = hrRepository;
    this.workerProxy = workerProxy;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return hrRepository.getHrTeamMember(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<HrTeamMember> getHrTeamMembers() {
    return hrRepository.getHrTeamMembers();
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) throws UserAlreadyExists {
    final User user = hrTeamMember.getUser();
    if (!userService.isUsernameFree(user.getUsername())) {
      throw new UserAlreadyExists(
          messageSource.getMessage("error.useralreadyexist", null, Locale.getDefault()));
    }
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
    final HrTeamMember hrTeamMember = getHrTeamMember(subjectId);
    hrRepository.deleteHrTeamMember(hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void addManagerToHr(final long hrTeamMemberId, final long managerId)
      throws SubjectDoesNotExistException {
    final HrTeamMember hrTeamMember = hrRepository.getHrTeamMember(hrTeamMemberId);
    if (hrTeamMember == null) {
      throw new SubjectDoesNotExistException(
          messageSource.getMessage("error.hrteammemberdoesnotexist", null, Locale.getDefault()));
    }
    final Manager manager = workerProxy.getManager(managerId);
    if (manager == null) {
      throw new SubjectDoesNotExistException(
          messageSource.getMessage("error.managerdoesnotexist", null, Locale.getDefault()));
    }
    hrRepository.addManagerToHr(hrTeamMember, manager);
  }
}
