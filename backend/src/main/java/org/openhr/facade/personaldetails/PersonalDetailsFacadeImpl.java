package org.openhr.facade.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.Subject;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PersonalDetailsFacadeImpl implements PersonalDetailsFacade {

  private final PersonalDetailsService personalDetailsService;

  public PersonalDetailsFacadeImpl(final PersonalDetailsService myDetailsService) {
    this.personalDetailsService = myDetailsService;
  }

  @Override
  @Transactional(readOnly = true)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return this.personalDetailsService.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional
  public void addSubject(Subject subject) throws HibernateException {
    this.personalDetailsService.addSubject(subject);
  }
}
