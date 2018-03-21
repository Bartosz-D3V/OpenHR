package org.openhr.application.hr.repository;

import org.openhr.application.hr.dao.HrDAO;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HrRepository {
  private final HrDAO hrDAO;

  public HrRepository(final HrDAO hrDAO) {
    this.hrDAO = hrDAO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return hrDAO.getHrTeamMember(subjectId);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) {
    return hrDAO.addHrTeamMember(hrTeamMember);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    return hrDAO.updateHrTeamMember(subjectId, hrTeamMember);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteHrTeamMember(final long subjectId) {
    hrDAO.deleteHrTeamMember(subjectId);
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void addManagerToHr(final HrTeamMember hrTeamMember, final Manager manager) {
    hrDAO.addManagerToHr(hrTeamMember, manager);
  }
}
