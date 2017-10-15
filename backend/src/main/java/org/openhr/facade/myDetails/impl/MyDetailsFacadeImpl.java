package org.openhr.facade.myDetails.impl;

import org.openhr.domain.subject.Subject;
import org.openhr.facade.myDetails.MyDetailsFacade;
import org.openhr.service.myDetails.MyDetailsService;

public class MyDetailsFacadeImpl implements MyDetailsFacade {

  private final MyDetailsService myDetailsService;

  public MyDetailsFacadeImpl(final MyDetailsService myDetailsService) {
    this.myDetailsService = myDetailsService;
  }

  @Override
  public Subject getSubjectDetails(final long subjectId) {
    return this.myDetailsService.getSubjectDetails(subjectId);
  }
}
