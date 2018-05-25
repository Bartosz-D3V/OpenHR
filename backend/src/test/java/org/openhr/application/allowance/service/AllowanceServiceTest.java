package org.openhr.application.allowance.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.allowance.repository.AllowanceRepository;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.holiday.service.HolidayService;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class AllowanceServiceTest {
  @Autowired private AllowanceService allowanceService;

  @MockBean private AllowanceRepository allowanceRepository;

  @MockBean private SubjectService subjectService;

  @MockBean private HolidayService holidayService;

  @Test
  public void getLeftAllowanceInDaysShouldReturnDiffBetweenAllowedLeaveAndUsedLeave() {
    when(allowanceRepository.getAllowance(100L)).thenReturn(25L);
    when(allowanceRepository.getUsedAllowance(100L)).thenReturn(10L);

    assertEquals(15L, allowanceService.getLeftAllowanceInDays(100L));
  }

  @Test
  public void subtractDaysFromSubjectAllowanceExcludingFreeDaysShouldUpdateHRInformation()
      throws ValidationException {
    when(holidayService.getWorkingDaysInBetween(anyObject(), anyObject())).thenReturn(4L);
    when(allowanceRepository.getAllowance(anyLong())).thenReturn(25L);
    when(allowanceRepository.getUsedAllowance(anyLong())).thenReturn(0L);

    final Subject subject = new Employee();
    final HrInformation hrInformation = new HrInformation();
    hrInformation.setAllowance(25);
    subject.setHrInformation(hrInformation);
    final LeaveApplication leaveApplication =
        new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(4));

    final Subject updatedSubject =
        allowanceService.subtractDaysFromSubjectAllowanceExcludingFreeDays(
            subject, leaveApplication);

    assertEquals(subject.getHrInformation(), updatedSubject.getHrInformation());
  }

  @Test(expected = ValidationException.class)
  public void
      subtractDaysFromSubjectAllowanceExcludingFreeDaysShouldThrowErrorIfLeaveWouldExceedLeaveAllowance()
          throws ValidationException {
    when(holidayService.getWorkingDaysInBetween(anyObject(), anyObject())).thenReturn(4L);
    when(allowanceRepository.getAllowance(anyLong())).thenReturn(20L);
    when(allowanceRepository.getUsedAllowance(anyLong())).thenReturn(17L);

    final Subject subject = new Employee();
    final HrInformation hrInformation = new HrInformation();
    hrInformation.setAllowance(25);
    subject.setHrInformation(hrInformation);
    final LeaveApplication leaveApplication =
        new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(4));

    allowanceService.subtractDaysFromSubjectAllowanceExcludingFreeDays(
        new Employee(), leaveApplication);
  }
}
