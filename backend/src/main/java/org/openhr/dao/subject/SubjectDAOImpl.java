package org.openhr.dao.subject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SubjectDAOImpl implements SubjectDAO {

  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public SubjectDAOImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException, HibernateException {
    Subject subject;
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      subject = session.get(Subject.class, subjectId);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }
    if (subject == null) {
      log.error("Subject could not be found, although it must exists at this point");
      throw new SubjectDoesNotExistException("Subject could not be found");
    }

    return subject;
  }

  @Override
  @Transactional
  public void addSubject(final Subject subject) throws HibernateException {
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.save(subject);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }
  }

  @Override
  @Transactional
  public void updateSubject(final long subjectId, final Subject subject) throws HibernateException,
    SubjectDoesNotExistException {
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      final Subject legacySubject = this.getSubjectDetails(subjectId);
      legacySubject.setFirstName(subject.getFirstName());
      legacySubject.setLastName(subject.getLastName());
      legacySubject.setRole(subject.getRole());
      legacySubject.setPersonalInformation(subject.getPersonalInformation());
      legacySubject.setContactInformation(subject.getContactInformation());
      legacySubject.setEmployeeInformation(subject.getEmployeeInformation());
      session.update(legacySubject);
      transaction.commit();
      session.close();
    } catch (HibernateException hibernateException) {
      log.error("Issue occurred during the update of the subject");
      log.error(hibernateException.getMessage());
      throw hibernateException;
    } catch (SubjectDoesNotExistException subjectDoesNotExistException) {
      log.error(subjectDoesNotExistException.getMessage());
      throw subjectDoesNotExistException;
    }
  }

  @Override
  @Transactional
  public void updateSubjectPersonalInformation(final long subjectId, final PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = this.getSubjectDetails(subjectId);
    subject.setPersonalInformation(personalInformation);
    this.mergeSubject(subject);
  }

  @Override
  @Transactional
  public void updateSubjectContactInformation(final long subjectId, final ContactInformation contactInformation)
    throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = this.getSubjectDetails(subjectId);
    subject.setContactInformation(contactInformation);
    this.mergeSubject(subject);
  }

  @Override
  @Transactional
  public void updateSubjectEmployeeInformation(final long subjectId, final EmployeeInformation employeeInformation)
    throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = this.getSubjectDetails(subjectId);
    subject.setEmployeeInformation(employeeInformation);
    this.mergeSubject(subject);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteSubject(final long subjectId) throws HibernateException, SubjectDoesNotExistException {
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.delete(this.getSubjectDetails(subjectId));
      transaction.commit();
      session.close();
    } catch (HibernateException hibernateException) {
      log.error("Issue occurred during the deletion of the subject");
      log.error(hibernateException.getMessage());
      throw hibernateException;
    } catch (SubjectDoesNotExistException subjectDoesNotExistException) {
      log.error(subjectDoesNotExistException.getMessage());
      throw subjectDoesNotExistException;
    }
  }

  private void mergeSubject(final Subject subject) throws HibernateException {
    try {
      Session session = sessionFactory.openSession();
      Transaction transaction = session.beginTransaction();
      session.merge(subject);
      transaction.commit();
      session.close();
    } catch (final HibernateException hibernateException) {
      log.error("Issue occurred during the update of the subject");
      log.error(hibernateException.getMessage());
      throw hibernateException;
    }
  }
}
