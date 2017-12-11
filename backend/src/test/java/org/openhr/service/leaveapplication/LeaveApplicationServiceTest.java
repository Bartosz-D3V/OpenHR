package org.openhr.service.leaveapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.dao.leaveapplication.LeaveApplicationDAO;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeaveApplicationServiceTest {

  private final LeaveApplication mockLeaveApplication = new LeaveApplication();
  private LeaveApplicationServiceImpl leaveApplicationServiceImpl;

  @Autowired
  private LeaveApplicationService leaveApplicationService;

  @MockBean
  private LeaveApplicationDAO leaveApplicationDAO;

  @Before()
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    leaveApplicationServiceImpl = new LeaveApplicationServiceImpl(leaveApplicationDAO);
  }

  @Test
  public void rejectApplicationShouldMarkApplicationAsApprovedByManager() {
    mockLeaveApplication.setApprovedByManager(true);
    final LeaveApplication leaveApplication = leaveApplicationServiceImpl.rejectApplication(Role.MANAGER,
      mockLeaveApplication);

    assertFalse(leaveApplication.isApprovedByManager());
  }

  @Test
  public void rejectApplicationShouldMarkApplicationAsApprovedByHRTeamMember() {
    mockLeaveApplication.setApprovedByHR(true);
    final LeaveApplication leaveApplication = leaveApplicationServiceImpl.rejectApplication(Role.HRTEAMMEMBER,
      mockLeaveApplication);

    assertFalse(leaveApplication.isApprovedByHR());
  }

  @Test
  public void approveApplicationShouldMarkApplicationAsApprovedByManager() {
    mockLeaveApplication.setApprovedByManager(true);
    final LeaveApplication leaveApplication = leaveApplicationServiceImpl.approveLeaveApplication(Role.MANAGER,
      mockLeaveApplication);

    assertTrue(leaveApplication.isApprovedByManager());
  }

  @Test
  public void approveApplicationShouldMarkApplicationAsApprovedByHRTeamMember() {
    mockLeaveApplication.setApprovedByHR(true);
    final LeaveApplication leaveApplication = leaveApplicationServiceImpl.approveLeaveApplication(Role.HRTEAMMEMBER,
      mockLeaveApplication);

    assertTrue(leaveApplication.isApprovedByHR());
  }
}
