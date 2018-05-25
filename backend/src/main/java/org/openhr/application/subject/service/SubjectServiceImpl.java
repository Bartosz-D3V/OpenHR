package org.openhr.application.subject.service;

import java.util.List;
import java.util.Optional;
import org.hibernate.HibernateException;
import org.openhr.application.holiday.service.HolidayService;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.application.subject.repository.SubjectRepository;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.proxy.worker.WorkerProxy;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectServiceImpl implements SubjectService {
  private final SubjectRepository subjectRepository;
  private final WorkerProxy workerProxy;
  private final HolidayService holidayService;
  private final MessageSource messageSource;

  public SubjectServiceImpl(
      final SubjectRepository subjectRepository,
      final WorkerProxy workerProxy,
      final HolidayService holidayService,
      final MessageSource messageSource) {
    this.subjectRepository = subjectRepository;
    this.workerProxy = workerProxy;
    this.holidayService = holidayService;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Subject> getSubjects() {
    return subjectRepository.getSubjects();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return subjectRepository.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetailsByEmail(final String email, final Optional<String> excludeEmail)
      throws SubjectDoesNotExistException {
    return subjectRepository.getSubjectDetailsByEmail(email, excludeEmail);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectPersonalInformation(
      final long subjectId, final PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectRepository.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectContactInformation(
      final long subjectId, final ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectRepository.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectEmployeeInformation(
      final long subjectId, final EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectRepository.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateSubjectHRInformation(final long subjectId, final HrInformation hrInformation) {
    subjectRepository.updateSubjectHRInformation(subjectId, hrInformation);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LightweightSubjectDTO getLightweightSubject(final long subjectId)
      throws SubjectDoesNotExistException {
    return subjectRepository.getLightweightSubject(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<LightweightSubjectDTO> getLightweightSubjects() {
    return subjectRepository.getLightweightSubjects();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Role getSubjectRole(final long subjectId) throws SubjectDoesNotExistException {
    return getSubjectDetails(subjectId).getRole();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectSupervisor(final long subjectId) throws SubjectDoesNotExistException {
    final Role subjectRole = getSubjectRole(subjectId);
    switch (subjectRole) {
      case EMPLOYEE:
        return workerProxy.getEmployee(subjectId).getManager();
      case MANAGER:
        return workerProxy.getManager(subjectId).getHrTeamMember();
      case HRTEAMMEMBER:
        return workerProxy.getHrTeamMember(subjectId);
      default:
        return null;
    }
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void updateEmail(final long subjectId, final String updatedEmail) {
    subjectRepository.updateEmail(subjectId, updatedEmail);
  }
}
