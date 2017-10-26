package org.openhr.service.personaldetails;

import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.dao.subject.SubjectDAO;
import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

  private final SubjectDAO subjectDAO;

  public PersonalDetailsServiceImpl(final SubjectDAO subjectDAO) {
    this.subjectDAO = subjectDAO;
  }

  @Override
  public Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException {
    return this.subjectDAO.getSubjectDetails(subjectId);
  }

}
