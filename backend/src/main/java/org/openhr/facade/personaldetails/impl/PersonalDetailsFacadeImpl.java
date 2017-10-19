package org.openhr.facade.personaldetails.impl;

import org.openhr.domain.subject.Subject;
import org.openhr.facade.personaldetails.PersonalDetailsFacade;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Component;

@Component
public class PersonalDetailsFacadeImpl implements PersonalDetailsFacade {

  private final PersonalDetailsService personalDetailsService;

  public PersonalDetailsFacadeImpl(final PersonalDetailsService personalDetailsService) {
    this.personalDetailsService = personalDetailsService;
  }

  @Override
  public Subject getSubjectDetails(final long subjectId) {
    return this.personalDetailsService.getSubjectDetails(subjectId);
  }
}
