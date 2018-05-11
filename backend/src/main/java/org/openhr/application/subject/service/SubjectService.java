package org.openhr.application.subject.service;

import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;

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

  LightweightSubjectDTO getLightweightSubject(long subjectId) throws SubjectDoesNotExistException;

  List<LightweightSubjectDTO> getLightweightSubjects();

  long getLeftAllowanceInDays(long subjectId);

  long getUsedAllowance(long subjectId);

  Subject subtractDaysFromSubjectAllowanceExcludingFreeDays(
      Subject subject, LeaveApplication leaveApplication) throws ValidationException;

  void revertSubtractedDaysForApplication(Subject subject, LeaveApplication leaveApplication);

  Role getSubjectRole(long subjectId) throws SubjectDoesNotExistException;

  Subject getSubjectSupervisor(long subjectId) throws SubjectDoesNotExistException;
}
