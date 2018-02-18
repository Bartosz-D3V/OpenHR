package org.openhr.application.subject.service;

import org.hibernate.HibernateException;
import org.openhr.application.authentication.service.AuthenticationService;
import org.openhr.application.holiday.service.HolidayService;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.subject.dao.SubjectDAO;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectServiceImpl implements SubjectService {

  private final SubjectDAO subjectDAO;
  private final AuthenticationService authenticationService;
  private final HolidayService holidayService;

  public SubjectServiceImpl(final SubjectDAO subjectDAO,
                            final AuthenticationService authenticationService,
                            final HolidayService holidayService) {
    this.subjectDAO = subjectDAO;
    this.authenticationService = authenticationService;
    this.holidayService = holidayService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Subject getSubjectDetails(final long subjectId) throws SubjectDoesNotExistException {
    return subjectDAO.getSubjectDetails(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateSubjectPersonalInformation(final long subjectId, final PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateSubjectContactInformation(final long subjectId, final ContactInformation contactInformation)
    throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateSubjectEmployeeInformation(final long subjectId, final EmployeeInformation employeeInformation)
    throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void deleteSubject(final long subjectId) throws HibernateException, SubjectDoesNotExistException {
    subjectDAO.deleteSubject(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public LightweightSubjectDTO getLightweightSubject(final long subjectId) throws SubjectDoesNotExistException {
    return subjectDAO.getLightweightSubject(subjectId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long getLeftAllowanceInDays(final long subjectId) {
    return subjectDAO.getAllowance(subjectId) - subjectDAO.getUsedAllowance(subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void subtractDaysExcludingFreeDays(final Subject subject, final LeaveApplication leaveApplication)
    throws ValidationException {
    final long allowanceToSubtract = holidayService.getWorkingDaysBetweenIncl(leaveApplication.getStartDate(),
      leaveApplication.getEndDate());
    final long currentlyUsedAllowance = getLeftAllowanceInDays(subject.getSubjectId());
    final long newUsedAllowance = currentlyUsedAllowance - allowanceToSubtract;
    if (newUsedAllowance < 0) {
      throw new ValidationException("Not enough leave allowance");
    }
    if (allowanceToSubtract > subject.getHrInformation().getAllowance()) {
      throw new ValidationException("Leave is too long");
    }
    subject.getHrInformation().setUsedAllowance(newUsedAllowance);
    subjectDAO.updateSubjectHRInformation(subject.getSubjectId(), subject.getHrInformation());
  }
}
