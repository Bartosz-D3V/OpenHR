package org.openhr.service.myDetails;

import org.openhr.domain.subject.Subject;

public interface MyDetailsService {
  Subject getSubjectDetails(long subjectId);
}
