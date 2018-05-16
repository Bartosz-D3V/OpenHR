package org.openhr.application.subject.facade;

import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SubjectFacadeImpl implements SubjectFacade {

  private final SubjectService subjectService;

  public SubjectFacadeImpl(final SubjectService myDetailsService) {
    this.subjectService = myDetailsService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Subject> getSubjects() {
    return subjectService.getSubjects();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return subjectService.getSubjectDetails(subjectId);
  }

  @Override
  public Subject getSubjectDetailsByEmail(final String email, final Optional<String> excludeEmail)
      throws SubjectDoesNotExistException {
    return subjectService.getSubjectDetailsByEmail(email, excludeEmail);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateSubjectPersonalInformation(
      final long subjectId, final PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectService.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateSubjectContactInformation(
      final long subjectId, final ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectService.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateSubjectEmployeeInformation(
      final long subjectId, final EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectService.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LightweightSubjectDTO getLightweightSubject(final long subjectId)
      throws SubjectDoesNotExistException {
    return subjectService.getLightweightSubject(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LightweightSubjectDTO> getLightweightSubjects() {
    return subjectService.getLightweightSubjects();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateEmail(final long subjectId, final String updatedEmail)
      throws SubjectDoesNotExistException {
    subjectService.updateEmail(subjectId, updatedEmail);
  }
}
