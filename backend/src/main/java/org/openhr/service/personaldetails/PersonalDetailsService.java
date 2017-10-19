package org.openhr.service.personaldetails;

import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Service;

public interface PersonalDetailsService {
  Subject getSubjectDetails(long subjectId);
}
