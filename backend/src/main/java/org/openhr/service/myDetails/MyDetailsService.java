package org.openhr.service.myDetails;

import org.openhr.domain.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public interface MyDetailsService {
  Subject getSubjectDetails(long subjectId);
}
