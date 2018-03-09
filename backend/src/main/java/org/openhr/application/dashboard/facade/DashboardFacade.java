package org.openhr.application.dashboard.facade;

import org.openhr.application.dashboard.dto.MonthSummaryDTO;

import java.util.List;

public interface DashboardFacade {
  List<MonthSummaryDTO> getMonthlySummaryReport(long subjectId);
}
