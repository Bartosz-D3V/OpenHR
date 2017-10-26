package org.openhr.facade.personaldetails;

import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.Subject;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Component;

@Component
public class PersonalDetailsFacadeImpl implements PersonalDetailsFacade {

  private final PersonalDetailsService personalDetailsService;

  public PersonalDetailsFacadeImpl(final PersonalDetailsService myDetailsService) {
    this.personalDetailsService = myDetailsService;
  }

  @Override
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return this.personalDetailsService.getSubjectDetails(subjectId);
  }
}
