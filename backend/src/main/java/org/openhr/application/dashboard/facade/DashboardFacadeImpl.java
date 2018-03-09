package org.openhr.application.dashboard.facade;

import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardFacadeImpl implements DashboardFacade {
  private final DashboardService dashboardService;

  public DashboardFacadeImpl(final DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<MonthSummaryDTO> getMonthlySummaryReport(final long subjectId) {
    return dashboardService.getMonthlySummaryReport(subjectId);
  }
}
