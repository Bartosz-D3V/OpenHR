package org.openhr.application.dashboard.facade;

import java.util.List;
import org.openhr.application.dashboard.dto.DelegationExpenditureDTO;
import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.dto.StatusRatioDTO;
import org.openhr.application.dashboard.service.DashboardService;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public StatusRatioDTO getCurrentYearStatusRatio() {
    return dashboardService.getCurrentYearStatusRatio();
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public DelegationExpenditureDTO getDelegationExpenditures(final int year) {
    return dashboardService.getDelegationExpenditures(year);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Subject> retrieveSubjectsOnLeaveToday() {
    return dashboardService.retrieveSubjectsOnLeaveToday();
  }
}
