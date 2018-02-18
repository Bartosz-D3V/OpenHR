package org.openhr.common.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract class that shall replace repetitive, simple Hibernate queries.
 */
public abstract class BaseDAO {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  protected BaseDAO(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  protected void save(final Object object) throws HibernateException {
    try {
      final Session session = sessionFactory.openSession();
      session.save(object);
      session.flush();
      session.close();
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
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getLocalizedMessage());
      throw hibernateException;
    }

    return object;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  protected void merge(final Object object) throws HibernateException {
    try {
      final Session session = sessionFactory.openSession();
      session.merge(object);
      session.flush();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getLocalizedMessage());
      throw hibernateException;
    }
  }
}
