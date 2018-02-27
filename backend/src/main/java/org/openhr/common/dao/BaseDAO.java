package org.openhr.common.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openhr.application.hr.domain.HrTeamMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract class that shall replace repetitive, simple Hibernate queries.
 */
@Transactional
public abstract class BaseDAO {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  protected BaseDAO(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  protected void save(final Object object) throws HibernateException {
    try {
      final Session session = sessionFactory.getCurrentSession();
      session.save(object);
      session.flush();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getLocalizedMessage());
      throw hibernateException;
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  protected Object get(final Class objectClass, final long id) throws HibernateException {
    Object object;
    try {
      final Session session = sessionFactory.getCurrentSession();
      object = session.get(objectClass, id);
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getLocalizedMessage());
      throw hibernateException;
    }

    return object;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  protected void merge(final Object object) throws HibernateException {
    try {
      final Session session = sessionFactory.getCurrentSession();
      session.merge(object);
      session.flush();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getLocalizedMessage());
      throw hibernateException;
    }
  }
}
