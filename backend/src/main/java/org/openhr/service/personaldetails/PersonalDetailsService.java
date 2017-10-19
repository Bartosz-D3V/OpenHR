package org.openhr.service.personaldetails;

import org.openhr.domain.subject.Subject;

public interface PersonalDetailsService {
  Subject getSubjectDetails(long subjectId);
}
