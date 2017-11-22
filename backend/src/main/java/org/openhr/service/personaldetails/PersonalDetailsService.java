package org.openhr.service.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;

public interface PersonalDetailsService {
  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;

  void addSubject(Subject subject) throws HibernateException;

  void updateSubject(long subjectId, Subject subject) throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectPersonalInformation(long subjectId, PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectContactInformation(long subjectId, ContactInformation contactInformation)
    throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectEmployeeInformation(long subjectId, EmployeeInformation employeeInformation)
    throws HibernateException, SubjectDoesNotExistException;

  void deleteSubject(long subjectId) throws HibernateException, SubjectDoesNotExistException;
}
