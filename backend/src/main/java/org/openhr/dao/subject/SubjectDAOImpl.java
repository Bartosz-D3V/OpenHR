package org.openhr.dao.subject;

import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDAOImpl implements SubjectDAO {

  @Override
  public Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException {
    return null;
  }
}
