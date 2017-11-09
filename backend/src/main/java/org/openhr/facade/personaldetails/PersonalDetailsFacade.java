package org.openhr.facade.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.Subject;

public interface PersonalDetailsFacade {
  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;

  void addSubject(Subject subject) throws HibernateException;

  void updateSubject(Subject subject) throws HibernateException;

  void updateSubjectAddress(long subjectId, Address address) throws HibernateException, SubjectDoesNotExistException;
}
