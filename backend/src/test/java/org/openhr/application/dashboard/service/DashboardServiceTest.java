package org.openhr.application.dashboard.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.dto.StatusRatioDTO;
import org.openhr.application.dashboard.repository.DashboardRepository;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DashboardServiceTest {
  private final List<LeaveApplication> leaveApplications = new ArrayList<>();

  @Autowired private DashboardService dashboardService;

  @MockBean private DashboardRepository dashboardRepository;

  @Before
  public void setUp() {
    final LeaveApplication leaveApplication1 = new LeaveApplication();
    leaveApplication1.setStartDate(LocalDate.of(2018, Month.MAY, 5));
    leaveApplication1.setEndDate(LocalDate.of(2018, Month.JUNE, 1));
    leaveApplication1.setTerminated(true);
    leaveApplication1.setApprovedByHR(true);
    final LeaveApplication leaveApplication2 = new LeaveApplication();
    leaveApplication2.setStartDate(LocalDate.of(2018, Month.DECEMBER, 5));
    leaveApplication2.setEndDate(LocalDate.of(2019, Month.JANUARY, 1));
    leaveApplication2.setTerminated(true);
    leaveApplication2.setApprovedByHR(true);
    final LeaveApplication leaveApplication3 = new LeaveApplication();
    leaveApplication3.setStartDate(LocalDate.of(2018, Month.AUGUST, 1));
    leaveApplication3.setEndDate(LocalDate.of(2018, Month.AUGUST, 9));
    leaveApplication3.setTerminated(true);
    leaveApplication3.setApprovedByHR(false);

    leaveApplications.add(leaveApplication1);
    leaveApplications.add(leaveApplication2);
    leaveApplications.add(leaveApplication3);
  }

  @Test
  public void getMonthlySummaryReportReturnObjectShouldBeOfSize12() {
    when(dashboardRepository.getApplicationsInCurrentYear(1L)).thenReturn(leaveApplications);
    final List<MonthSummaryDTO> result = dashboardService.getMonthlySummaryReport(1L);

    assertEquals(12, result.size());
  }

  @Test
  public void getMonthlySummaryReportShouldConvertLeaveApplicationObjectsToMonthSummaryDTOS() {
    when(dashboardRepository.getApplicationsInCurrentYear(1L)).thenReturn(leaveApplications);
    final List<MonthSummaryDTO> result = dashboardService.getMonthlySummaryReport(1L);

    assertEquals(1, result.get(0).getNumberOfApplications());
    assertEquals(0, result.get(1).getNumberOfApplications());
    assertEquals(0, result.get(2).getNumberOfApplications());
    assertEquals(0, result.get(3).getNumberOfApplications());
    assertEquals(27, result.get(4).getNumberOfApplications());
    assertEquals(1, result.get(5).getNumberOfApplications());
    assertEquals(0, result.get(6).getNumberOfApplications());
    assertEquals(9, result.get(7).getNumberOfApplications());
    assertEquals(0, result.get(8).getNumberOfApplications());
    assertEquals(0, result.get(9).getNumberOfApplications());
    assertEquals(0, result.get(10).getNumberOfApplications());
    assertEquals(27, result.get(11).getNumberOfApplications());

    assertEquals(Month.MAY, result.get(4).getMonth());
    assertEquals(Month.AUGUST, result.get(7).getMonth());
  }

  @Test
  public void getCurrentYearStatusRatioShouldConvertLeaveApplicationsToStatusRatioDTO() {
    when(dashboardRepository.getCurrentYearStatusRatio()).thenReturn(leaveApplications);
    final StatusRatioDTO result = dashboardService.getCurrentYearStatusRatio();

    assertEquals(2, result.getAccepted());
    assertEquals(1, result.getRejected());
  }
}
