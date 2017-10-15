package org.openhr.facade.myDetails;

import org.openhr.domain.subject.Subject;

public interface MyDetailsFacade {
  Subject getSubjectDetails(long subjectId);
}
