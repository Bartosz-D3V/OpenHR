package org.openhr.application.hr.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HrDAOImpl extends BaseDAO implements HrDAO {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public HrDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return (HrTeamMember) super.get(HrTeamMember.class, subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) {
    super.save(hrTeamMember);

    return hrTeamMember;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    final HrTeamMember savedHrTeamMember = getHrTeamMember(subjectId);
    BeanUtils.copyProperties(hrTeamMember, savedHrTeamMember, "subjectId");
    super.merge(savedHrTeamMember);

    return savedHrTeamMember;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
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
}
