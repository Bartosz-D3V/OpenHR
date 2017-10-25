package org.openhr.facade.personaldetails;

import org.openhr.domain.subject.Subject;

public interface PersonalDetailsFacade {
  Subject getSubjectDetails(long subjectId);
}
