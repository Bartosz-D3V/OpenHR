package org.openhr.leaveapplication.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.leaveapplication.repository.LeaveApplicationRepository;
import org.openhr.application.leaveapplication.service.LeaveApplicationService;
import org.openhr.application.leaveapplication.service.LeaveApplicationServiceImpl;
import org.openhr.application.leaveapplication.dao.LeaveApplicationDAO;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.enumeration.Role;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
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
    leaveApplicationService.createLeaveApplication(new Subject(), leaveApplication);
  }

  @Test(expected = ValidationException.class)
  public void createLeaveApplicationShouldThrowExceptionIfLeftAllowanceIsNotSufficient() throws ValidationException {
    when(subjectService.getLeftAllowanceInDays(anyLong())).thenReturn(4L);

    final LeaveApplication leaveApplication = new LeaveApplication(LocalDate.now(), LocalDate.now().plusDays(5));
    leaveApplicationService.createLeaveApplication(new Subject(), leaveApplication);
  }

  @Test
  public void rejectLeaveApplicationShouldMarkApplicationAsApprovedByManager() throws ApplicationDoesNotExistException {
    when(leaveApplicationDAO.getLeaveApplication(1L)).thenReturn(mockLeaveApplication);

    mockLeaveApplication.setApprovedByManager(true);
    leaveApplicationServiceImpl.rejectLeaveApplication(Role.MANAGER, mockLeaveApplication.getApplicationId());

    assertFalse(mockLeaveApplication.isApprovedByManager());
  }

  @Test
  public void rejectLeaveApplicationShouldMarkApplicationAsApprovedByHRTeamMember() throws ApplicationDoesNotExistException {
    when(leaveApplicationDAO.getLeaveApplication(1L)).thenReturn(mockLeaveApplication);

    mockLeaveApplication.setApprovedByHR(true);
    leaveApplicationServiceImpl.rejectLeaveApplication(Role.HRTEAMMEMBER, mockLeaveApplication.getApplicationId());

    assertFalse(mockLeaveApplication.isApprovedByHR());
  }

  @Test
  public void approveApplicationShouldMarkApplicationAsApprovedByManager() throws ApplicationDoesNotExistException {
    when(leaveApplicationDAO.getLeaveApplication(1L)).thenReturn(mockLeaveApplication);

    mockLeaveApplication.setApprovedByManager(true);
    leaveApplicationServiceImpl.approveLeaveApplication(Role.MANAGER, mockLeaveApplication.getApplicationId());

    assertTrue(mockLeaveApplication.isApprovedByManager());
  }

  @Test
  public void approveApplicationShouldMarkApplicationAsApprovedByHRTeamMember() throws ApplicationDoesNotExistException {
    when(leaveApplicationDAO.getLeaveApplication(1L)).thenReturn(mockLeaveApplication);

    mockLeaveApplication.setApprovedByHR(true);
    leaveApplicationServiceImpl.approveLeaveApplication(Role.HRTEAMMEMBER, mockLeaveApplication.getApplicationId());

    assertTrue(mockLeaveApplication.isApprovedByHR());
  }
}
