package org.openhr.application.dashboard.facade;

import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.dto.StatusRatioDTO;

import java.util.List;

public interface DashboardFacade {
  List<MonthSummaryDTO> getMonthlySummaryReport(long subjectId);

  StatusRatioDTO getCurrentYearStatusRatio();
}
