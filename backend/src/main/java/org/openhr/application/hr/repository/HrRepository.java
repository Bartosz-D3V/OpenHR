package org.openhr.application.hr.repository;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.application.hr.dao.HrDAO;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HrRepository {
  private final HrDAO hrDAO;
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public HrRepository(final HrDAO hrDAO, final SessionFactory sessionFactory) {
    this.hrDAO = hrDAO;
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return hrDAO.getHrTeamMember(subjectId);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<HrTeamMember> getHrTeamMembers() {
    List<HrTeamMember> hrTeamMembers;
    try {
      final Session session = sessionFactory.getCurrentSession();
      hrTeamMembers = session.createCriteria(HrTeamMember.class).setReadOnly(true).list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return hrTeamMembers;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) {
    return hrDAO.addHrTeamMember(hrTeamMember);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    return hrDAO.updateHrTeamMember(subjectId, hrTeamMember);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteHrTeamMember(final HrTeamMember hrTeamMember)
      throws SubjectDoesNotExistException {
    hrDAO.deleteHrTeamMember(hrTeamMember);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void addManagerToHr(final HrTeamMember hrTeamMember, final Manager manager) {
    hrDAO.addManagerToHr(hrTeamMember, manager);
  }
}
