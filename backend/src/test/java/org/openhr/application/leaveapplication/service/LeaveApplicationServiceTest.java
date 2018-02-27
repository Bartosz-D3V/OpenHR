package org.openhr.application.leaveapplication.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.leaveapplication.dao.LeaveApplicationDAO;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.application.employee.domain.Employee;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeaveApplicationServiceTest {

  private final LeaveApplication mockLeaveApplication = new LeaveApplication();
  private LeaveApplicationServiceImpl leaveApplicationServiceImpl;

  @Autowired
  private LeaveApplicationService leaveApplicationService;

  @Autowired
  private LeaveApplicationRepository leaveApplicationRepository;

  @MockBean
  private SubjectService subjectService;

  @MockBean
  private LeaveApplicationDAO leaveApplicationDAO;

  @Before()
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    leaveApplicationServiceImpl = new LeaveApplicationServiceImpl(leaveApplicationDAO, leaveApplicationRepository,
      subjectService);
    mockLeaveApplication.setApplicationId(1L);
  }

  @Test(expected = ValidationException.class)
  public void createLeaveApplicationShouldThrowExceptionIfEndDateIsBeforeStartDate() throws ValidationException {
    final LeaveApplication leaveApplication = new LeaveApplication(LocalDate.now().plusDays(5), LocalDate.now());
    leaveApplicationService.createLeaveApplication(new Employee(), leaveApplication);
  }

  @Test(expected = ValidationException.class)
  public void createLeaveApplicationShouldThrowExceptionIfNoLeftAllowance() throws ValidationException {
    when(subjectService.getLeftAllowanceInDays(anyLong())).thenReturn(0L);

    final LeaveApplication leaveApplication = new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
    leaveApplicationService.createLeaveApplication(new Employee(), leaveApplication);
  }
}
