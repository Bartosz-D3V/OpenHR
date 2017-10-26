package org.openhr.dao.subject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SubjectDAOImpl implements SubjectDAO {

  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public SubjectDAOImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true)
  public Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException {
    return null;
  }

  @Override
  @Transactional
  public void addSubject(Subject subject) throws HibernateException {
    try {
      Session session = this.sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.save(subject);
      transaction.commit();
      session.close();
    } catch (HibernateException hibernateException) {
      this.log.error(hibernateException.getMessage());
      throw hibernateException;
    }
  }

}