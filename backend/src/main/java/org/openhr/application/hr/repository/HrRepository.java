package org.openhr.application.hr.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;
  private final HrDAO hrDAO;

  public HrRepository(final SessionFactory sessionFactory,
                      final HrDAO hrDAO) {
    this.sessionFactory = sessionFactory;
    this.hrDAO = hrDAO;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return hrDAO.getHrTeamMember(subjectId);
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
  public void deleteHrTeamMember(final long subjectId) throws SubjectDoesNotExistException {
    try {
      final Session session = sessionFactory.getCurrentSession();
      final HrTeamMember hrTeamMember = (HrTeamMember) session.createCriteria(HrTeamMember.class)
        .add(Restrictions.eq("subjectId", subjectId))
        .uniqueResult();
      if (hrTeamMember == null) {
        throw new SubjectDoesNotExistException("Subject does not exist");
      }
      session.delete(hrTeamMember);
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
    }
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void addManagerToHr(final HrTeamMember hrTeamMember, final Manager manager) {
    hrDAO.addManagerToHr(hrTeamMember, manager);
  }
}
