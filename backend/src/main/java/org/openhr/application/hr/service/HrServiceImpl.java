package org.openhr.application.hr.service;

import org.openhr.application.authentication.service.AuthenticationService;
import org.openhr.application.hr.dao.HrDAO;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.user.domain.User;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HrServiceImpl implements HrService {
  private final AuthenticationService authenticationService;
  private final HrDAO hrDAO;

  public HrServiceImpl(final AuthenticationService authenticationService,
                       final HrDAO hrDAO) {
    this.authenticationService = authenticationService;
    this.hrDAO = hrDAO;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return hrDAO.getHrTeamMember(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) {
    final User user = hrTeamMember.getUser();
    final String encodedPassword = authenticationService.encodePassword(user.getPassword());
    user.setPassword(encodedPassword);
    user.setUserRoles(authenticationService.setHrUserRole(user));
    hrTeamMember.setRole(Role.HRTEAMMEMBER);

    return hrDAO.addHrTeamMember(hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    return hrDAO.updateHrTeamMember(subjectId, hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteHrTeamMember(final long subjectId) throws SubjectDoesNotExistException {
    hrDAO.deleteHrTeamMember(subjectId);
  }
}
