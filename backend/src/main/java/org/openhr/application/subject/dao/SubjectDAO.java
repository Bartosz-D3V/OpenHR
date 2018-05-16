package org.openhr.application.subject.dao;

import org.hibernate.HibernateException;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface SubjectDAO {
  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;

  void updateSubjectPersonalInformation(long subjectId, PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectContactInformation(long subjectId, ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectEmployeeInformation(long subjectId, EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectHRInformation(long subjectId, HrInformation hrInformation)
      throws HibernateException;

  void updateEmail(long subjectId, String updatedEmail);
}
