package org.openhr.facade.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PersonalDetailsFacadeImpl implements PersonalDetailsFacade {

  private final PersonalDetailsService personalDetailsService;

  public PersonalDetailsFacadeImpl(final PersonalDetailsService myDetailsService) {
    this.personalDetailsService = myDetailsService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return personalDetailsService.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional
  public void addSubject(final Subject subject) throws HibernateException {
    personalDetailsService.addSubject(subject);
  }

  @Override
  @Transactional
  public void updateSubject(final long subjectId, final Subject subject) throws HibernateException,
    SubjectDoesNotExistException {
    personalDetailsService.updateSubject(subjectId, subject);
  }

  @Override
  @Transactional
  public void updateSubjectPersonalInformation(final long subjectId, final PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException {
    personalDetailsService.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Override
  @Transactional
  public void updateSubjectContactInformation(final long subjectId, final ContactInformation contactInformation)
    throws HibernateException, SubjectDoesNotExistException {
    personalDetailsService.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Override
  @Transactional
  public void updateSubjectEmployeeInformation(final long subjectId, final EmployeeInformation employeeInformation)
    throws HibernateException, SubjectDoesNotExistException {
    personalDetailsService.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteSubject(final long subjectId) throws HibernateException, SubjectDoesNotExistException {
    personalDetailsService.deleteSubject(subjectId);
  }
}
