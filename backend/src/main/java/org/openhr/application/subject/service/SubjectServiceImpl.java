package org.openhr.application.subject.service;

import org.hibernate.HibernateException;
import org.openhr.application.authentication.service.AuthenticationService;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.application.subject.dao.SubjectDAO;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectServiceImpl implements SubjectService {

  private final SubjectDAO subjectDAO;
  private final AuthenticationService authenticationService;

  public SubjectServiceImpl(final SubjectDAO subjectDAO,
                            final AuthenticationService authenticationService) {
    this.subjectDAO = subjectDAO;
    this.authenticationService = authenticationService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return subjectDAO.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createSubject(final Subject subject) throws HibernateException {
    final String encodedPassword = authenticationService.encodePassword(subject.getUser().getPassword());
    subject.getUser().setPassword(encodedPassword);
    subject.getUser().setUserRoles(authenticationService.setBasicUserRoles(subject.getUser()));
    subjectDAO.createSubject(subject);
  }

  @Override
  @Transactional
  public void updateSubject(final long subjectId, final Subject subject) throws HibernateException,
    SubjectDoesNotExistException {
    subjectDAO.updateSubject(subjectId, subject);
  }

  @Override
  @Transactional
  public void updateSubjectPersonalInformation(final long subjectId, final PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Override
  @Transactional
  public void updateSubjectContactInformation(final long subjectId, final ContactInformation contactInformation)
    throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Override
  @Transactional
  public void updateSubjectEmployeeInformation(final long subjectId, final EmployeeInformation employeeInformation)
    throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteSubject(final long subjectId) throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.deleteSubject(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LightweightSubjectDTO getLightweightSubject(final long subjectId) throws SubjectDoesNotExistException {
    return subjectDAO.getLightweightSubject(subjectId);
  }
}
