package org.openhr.dao.subject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.address.Address;
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
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException, HibernateException {
    Subject subject;
    try {
      Session session = this.sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      subject = session.get(Subject.class, subjectId);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      this.log.error(hibernateException.getMessage());
      throw hibernateException;
    }
    if (subject == null) {
      this.log.error("Subject could not be found, although it must exists at this point");
      throw new SubjectDoesNotExistException("Subject could not be found");
    }

    return subject;
  }

  @Override
  @Transactional
  public void addSubject(final Subject subject) throws HibernateException {
    try {
      Session session = this.sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.save(subject);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      this.log.error(hibernateException.getMessage());
      throw hibernateException;
    }
  }

  @Override
  @Transactional
  public void updateSubjectAddress(final long subjectId, final Address address) throws HibernateException,
          SubjectDoesNotExistException {
    final Subject subject = this.getSubjectDetails(subjectId);
    final Address updatedAddress = subject.getAddress();
    updatedAddress.setFirstLineAddress(address.getFirstLineAddress());
    updatedAddress.setSecondLineAddress(address.getSecondLineAddress());
    updatedAddress.setThirdLineAddress(address.getThirdLineAddress());
    updatedAddress.setPostcode(address.getPostcode());
    updatedAddress.setCity(address.getCity());
    updatedAddress.setCountry(address.getCountry());
    subject.setAddress(updatedAddress);
    try {
      Session session = this.sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.merge(subject);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      this.log.error("Issue occurred during the update of the subject's address");
      this.log.error(hibernateException.getMessage());
      throw hibernateException;
    }
  }
}