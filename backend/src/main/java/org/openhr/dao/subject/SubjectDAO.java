package org.openhr.dao.subject;

import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.Subject;

public interface SubjectDAO {
  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;
}
