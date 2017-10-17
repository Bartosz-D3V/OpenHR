package org.openhr.service.myDetails.impl;

import org.openhr.dao.subject.SubjectDAO;
import org.openhr.domain.subject.Subject;
import org.openhr.service.myDetails.MyDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyDetailsServiceImpl implements MyDetailsService {

  private final SubjectDAO subjectDAO;

  public MyDetailsServiceImpl(final SubjectDAO subjectDAO) {
    this.subjectDAO = subjectDAO;
  }

  @Override
  public Subject getSubjectDetails(final long subjectId) {
    return this.subjectDAO.getSubjectDetails(subjectId);
  }
}
