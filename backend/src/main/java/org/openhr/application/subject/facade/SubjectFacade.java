package org.openhr.application.subject.facade;

import java.util.List;
import org.hibernate.HibernateException;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface SubjectFacade {
  List<Subject> getSubjects();

  Subject getSubjectDetails(long subjectId) throws SubjectDoesNotExistException;

  void updateSubjectPersonalInformation(long subjectId, PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectContactInformation(long subjectId, ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void updateSubjectEmployeeInformation(long subjectId, EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException;

  void deleteSubject(long subjectId) throws HibernateException, SubjectDoesNotExistException;

  LightweightSubjectDTO getLightweightSubject(long subjectId) throws SubjectDoesNotExistException;

  List<LightweightSubjectDTO> getLightweightSubjects();
}
