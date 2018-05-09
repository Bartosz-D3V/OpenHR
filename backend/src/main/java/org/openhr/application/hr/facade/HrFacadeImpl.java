package org.openhr.application.hr.facade;

import java.util.List;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.hr.service.HrService;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HrFacadeImpl implements HrFacade {
  private final HrService hrService;

  public HrFacadeImpl(final HrService hrService) {
    this.hrService = hrService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return hrService.getHrTeamMember(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<HrTeamMember> getHrTeamMembers() {
    return hrService.getHrTeamMembers();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) throws UserAlreadyExists {
    return hrService.addHrTeamMember(hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    return hrService.updateHrTeamMember(subjectId, hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteHrTeamMember(final long subjectId) throws SubjectDoesNotExistException {
    hrService.deleteHrTeamMember(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addManagerToHr(final long hrTeamMemberId, final long managerId)
      throws SubjectDoesNotExistException {
    hrService.addManagerToHr(hrTeamMemberId, managerId);
  }
}
