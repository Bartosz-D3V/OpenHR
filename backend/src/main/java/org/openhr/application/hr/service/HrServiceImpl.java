package org.openhr.application.hr.service;

import org.openhr.application.hr.dao.HrDAO;
import org.openhr.application.hr.domain.HrTeamMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HrServiceImpl implements HrService {
  private final HrDAO hrDAO;

  public HrServiceImpl(final HrDAO hrDAO) {
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
    return hrDAO.addHrTeamMember(hrTeamMember);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    return hrDAO.updateHrTeamMember(subjectId, hrTeamMember);
  }
}
