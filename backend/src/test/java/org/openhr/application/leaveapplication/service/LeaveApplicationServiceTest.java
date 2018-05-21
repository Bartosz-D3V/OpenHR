package org.openhr.application.leaveapplication.service;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.allowance.service.AllowanceService;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LeaveApplicationServiceTest {

  private final LeaveApplication mockLeaveApplication = new LeaveApplication();

  @Autowired private LeaveApplicationService leaveApplicationService;

  @MockBean private AllowanceService allowanceService;

  @Before()
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockLeaveApplication.setApplicationId(1L);
  }

  @Test(expected = ValidationException.class)
  public void createLeaveApplicationShouldThrowExceptionIfEndDateIsBeforeStartDate()
      throws ValidationException {
    final LeaveApplication leaveApplication =
        new LeaveApplication(LocalDate.now().plusDays(5), LocalDate.now());
    leaveApplicationService.createLeaveApplication(new Employee(), leaveApplication);
  }

  @Test(expected = ValidationException.class)
  public void createLeaveApplicationShouldThrowExceptionIfNoLeftAllowance()
      throws ValidationException {
    when(allowanceService.getLeftAllowanceInDays(anyLong())).thenReturn(0L);

    final LeaveApplication leaveApplication =
        new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
    leaveApplicationService.createLeaveApplication(new Employee(), leaveApplication);
  }
}
