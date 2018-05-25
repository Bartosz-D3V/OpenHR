package org.openhr.application.subject.service;

import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface SubjectService {
  List<Subject> getSubjects();

  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;

  Subject getSubjectDetailsByEmail(String email, Optional<String> excludeEmail)
      throws SubjectDoesNotExistException;

  void updateSubjectPersonalInformation(long subjectId, PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectContactInformation(long subjectId, ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectEmployeeInformation(long subjectId, EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectHRInformation(long subjectId, HrInformation hrInformation);

  LightweightSubjectDTO getLightweightSubject(long subjectId) throws SubjectDoesNotExistException;

  List<LightweightSubjectDTO> getLightweightSubjects();

  Role getSubjectRole(long subjectId) throws SubjectDoesNotExistException;

  Subject getSubjectSupervisor(long subjectId) throws SubjectDoesNotExistException;

  void updateEmail(long subjectId, String updatedEmail) throws SubjectDoesNotExistException;
}
