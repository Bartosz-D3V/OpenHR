package org.openhr.service.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.dao.subject.SubjectDAO;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

  private final SubjectDAO subjectDAO;

  public PersonalDetailsServiceImpl(final SubjectDAO subjectDAO) {
    this.subjectDAO = subjectDAO;
  }

  @Override
  @Transactional(readOnly = true)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return this.subjectDAO.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional
  public void addSubject(final Subject subject) throws HibernateException {
    this.subjectDAO.addSubject(subject);
  }

  @Override
  @Transactional
  public void updateSubject(final Subject subject) throws HibernateException {
    this.subjectDAO.updateSubject(subject);
  }

  @Override
  @Transactional
  public void updateSubjectPersonalInformation(final long subjectId, final PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException {
    this.subjectDAO.updateSubjectPersonalInformation(subjectId, personalInformation);
  }
}
