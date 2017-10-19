package org.openhr.service.personaldetails.impl;

import org.openhr.dao.subject.SubjectDAO;
import org.openhr.domain.subject.Subject;
import org.openhr.service.personaldetails.PersonalDetailsService;
import org.springframework.stereotype.Service;

@Service
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

  private final SubjectDAO subjectDAO;

  public PersonalDetailsServiceImpl(final SubjectDAO subjectDAO) {
    this.subjectDAO = subjectDAO;
  }

  @Override
  public Subject getSubjectDetails(final long subjectId) {
    return this.subjectDAO.getSubjectDetails(subjectId);
  }
}
