package org.openhr.application.supervisor.repository;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.enumeration.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SupervisorRepository {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;

  public SupervisorRepository(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<Subject> getSupervisors() {
    List<Subject> supervisors;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Subject.class);
      supervisors =
          (List<Subject>)
              criteria
                  .add(
                      Restrictions.disjunction()
                          .add(Restrictions.eq("role", Role.MANAGER))
                          .add(Restrictions.eq("role", Role.HRTEAMMEMBER)))
                  .list();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return supervisors;
  }
}
