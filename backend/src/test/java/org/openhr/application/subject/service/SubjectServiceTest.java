package org.openhr.application.subject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.holiday.service.HolidayService;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.subject.repository.SubjectRepository;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class SubjectServiceTest {

  @Autowired
  private SubjectService subjectService;

  @MockBean
  private SubjectRepository subjectRepository;

  @MockBean
  private HolidayService holidayService;

  @Test
  public void getLeftAllowanceInDaysShouldReturnDiffBetweenAllowedLeaveAndUsedLeave() {
    when(subjectRepository.getAllowance(100L)).thenReturn(25L);
    when(subjectRepository.getUsedAllowance(100L)).thenReturn(10L);

    assertEquals(15L, subjectService.getLeftAllowanceInDays(100L));
  }

  @Test(expected = ValidationException.class)
  public void subtractDaysExcludingFreeDaysShouldThrowErrorIfLeaveWouldExceedLeftLeaveAllowance()
    throws ValidationException {
    when(holidayService.getWorkingDaysBetweenIncl(anyObject(), anyObject())).thenReturn(4L);
    when(subjectRepository.getAllowance(anyLong())).thenReturn(20L);
    when(subjectRepository.getUsedAllowance(anyLong())).thenReturn(17L);
    final LeaveApplication leaveApplication = new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(4));

    subjectService.subtractDaysFromSubjectAllowanceExcludingFreeDays(new Employee(), leaveApplication);
  }

  @Test(expected = ValidationException.class)
  public void subtractDaysExcludingFreeDaysShouldThrowErrorIfLeaveWouldExceedLeaveAllowance()
    throws ValidationException {
    when(holidayService.getWorkingDaysBetweenIncl(anyObject(), anyObject())).thenReturn(4L);
    when(subjectRepository.getAllowance(anyLong())).thenReturn(20L);
    when(subjectRepository.getUsedAllowance(anyLong())).thenReturn(17L);

    final Subject subject = new Employee();
    final HrInformation hrInformation = new HrInformation();
    hrInformation.setAllowance(25L);
    subject.setHrInformation(hrInformation);
    final LeaveApplication leaveApplication = new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(4));

    subjectService.subtractDaysFromSubjectAllowanceExcludingFreeDays(new Employee(), leaveApplication);
  }
}
