package org.openhr.application.dashboard.service;

import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.repository.DashboardRepository;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
  private final DashboardRepository dashboardRepository;

  public DashboardServiceImpl(final DashboardRepository dashboardRepository) {
    this.dashboardRepository = dashboardRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<MonthSummaryDTO> getMonthlySummaryReport(final long subjectId) {
    final List<LeaveApplication> leaveApplications = dashboardRepository.getApplicationsInCurrentYear(subjectId);
    return convertToMonthSummary(leaveApplications);
  }

  private List<MonthSummaryDTO> convertToMonthSummary(final List<LeaveApplication> leaveApplications) {
    final List<MonthSummaryDTO> result = new ArrayList<>();
    for (final Month month : Month.values()) {
      final MonthSummaryDTO monthSummaryDTO = new MonthSummaryDTO();
      final long monthCounter = leaveApplications
        .stream()
        .filter(leaveApplication -> leaveApplication.getStartDate().getMonth() == month)
        .count();
      monthSummaryDTO.setMonth(month);
      monthSummaryDTO.setNumberOfApplications(monthCounter);
      result.add(monthSummaryDTO);
    }
    return result;
  }
}
