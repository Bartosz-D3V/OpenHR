package org.openhr.service.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.Subject;

public interface PersonalDetailsService {
  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;

  void addSubject(Subject subject) throws HibernateException;
}
