package org.openhr.dao.subject;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;

public interface SubjectDAO {
  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;

  void addSubject(Subject subject) throws HibernateException;

  void updateSubject(long subjectId, Subject subject) throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectPersonalInformation(long subjectId, PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException;
}
