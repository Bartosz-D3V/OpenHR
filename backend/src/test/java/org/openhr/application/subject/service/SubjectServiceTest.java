package org.openhr.application.subject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.holiday.service.HolidayService;
import org.openhr.application.subject.dao.SubjectDAO;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubjectServiceTest {

  @Autowired
  private SubjectService subjectService;

  @MockBean
  private SubjectDAO subjectDAO;

  @MockBean
  private HolidayService holidayService;

  @Test
  public void getLeftAllowanceInDaysShouldReturnDiffBetweenAllowedLeaveAndUsedLeave() {
    when(subjectDAO.getAllowance(100L)).thenReturn(25L);
    when(subjectDAO.getUsedAllowance(100L)).thenReturn(10L);

    assertEquals(15L, subjectService.getLeftAllowanceInDays(100L));
  }

  @Test(expected = ValidationException.class)
  public void subtractDaysExcludingFreeDaysShouldThrowErrorIfLeaveWouldExceedLeftLeaveAllowance()
    throws ValidationException {
    when(holidayService.getWorkingDaysBetweenIncl(anyObject(), anyObject())).thenReturn(4L);
    when(subjectDAO.getAllowance(anyLong())).thenReturn(20L);
    when(subjectDAO.getUsedAllowance(anyLong())).thenReturn(17L);

    subjectService.subtractDaysExcludingFreeDays(new Subject(), LocalDate.now(), LocalDate.now().plusDays(4));
  }

  @Test(expected = ValidationException.class)
  public void subtractDaysExcludingFreeDaysShouldThrowErrorIfLeaveWouldExceedLeaveAllowance()
    throws ValidationException {
    when(holidayService.getWorkingDaysBetweenIncl(anyObject(), anyObject())).thenReturn(4L);
    when(subjectDAO.getAllowance(anyLong())).thenReturn(20L);
    when(subjectDAO.getUsedAllowance(anyLong())).thenReturn(17L);

    final Subject subject = new Subject();
    final HrInformation hrInformation = new HrInformation();
    hrInformation.setAllowance(25L);
    subject.setHrInformation(hrInformation);

    subjectService.subtractDaysExcludingFreeDays(subject, LocalDate.now(), LocalDate.now().plusDays(4));
  }
}