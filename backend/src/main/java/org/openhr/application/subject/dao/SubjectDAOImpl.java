package org.openhr.application.subject.dao;

import java.util.Locale;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SubjectDAOImpl extends BaseDAO implements SubjectDAO {

  private final SessionFactory sessionFactory;
  private final MessageSource messageSource;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public SubjectDAOImpl(final SessionFactory sessionFactory, final MessageSource messageSource) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId)
      throws SubjectDoesNotExistException, HibernateException {
    final Subject subject = (Subject) super.get(Subject.class, subjectId);
    if (subject == null) {
      log.error(messageSource.getMessage("error.subjectdoesnotexist", null, Locale.getDefault()));
      throw new SubjectDoesNotExistException("Subject could not be found");
    }

    return subject;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  protected Subject getExistingSubjectDetails(final long subjectId) throws HibernateException {
    return (Subject) super.get(Subject.class, subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectPersonalInformation(
      final long subjectId, final PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = getSubjectDetails(subjectId);
    subject.setPersonalInformation(personalInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectContactInformation(
      final long subjectId, final ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = getSubjectDetails(subjectId);
    subject.setContactInformation(contactInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectEmployeeInformation(
      final long subjectId, final EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException {
    final Subject subject = getSubjectDetails(subjectId);
    subject.setEmployeeInformation(employeeInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectHRInformation(final long subjectId, final HrInformation hrInformation)
      throws HibernateException {
    final Subject subject = getExistingSubjectDetails(subjectId);
    subject.setHrInformation(hrInformation);
    super.merge(subject);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateEmail(final long subjectId, final String updatedEmail) {
    final Subject subject = getExistingSubjectDetails(subjectId);
    subject.getContactInformation().setEmail(updatedEmail);
    super.merge(subject);
  }
}
