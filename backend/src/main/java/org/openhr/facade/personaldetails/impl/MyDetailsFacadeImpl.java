package org.openhr.facade.personaldetails.impl;

import org.openhr.domain.subject.Subject;
import org.openhr.facade.personaldetails.PersonalDetailsFacade;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyDetailsFacadeImpl implements PersonalDetailsFacade {

  private final PersonalDetailsService personalDetailsService;

  public MyDetailsFacadeImpl(final PersonalDetailsService myDetailsService) {
    this.personalDetailsService = myDetailsService;
  }

  @Override
  public Subject getSubjectDetails(final long subjectId) {
    return this.personalDetailsService.getSubjectDetails(subjectId);
  }
}
