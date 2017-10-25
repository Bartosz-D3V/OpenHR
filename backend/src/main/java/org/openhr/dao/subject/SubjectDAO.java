package org.openhr.dao.subject;

import org.openhr.domain.subject.Subject;

public interface SubjectDAO {
  public Subject getSubjectDetails(final long subjectId);
}
