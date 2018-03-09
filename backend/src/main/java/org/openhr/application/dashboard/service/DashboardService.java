package org.openhr.application.dashboard.service;

import org.openhr.application.dashboard.dto.MonthSummaryDTO;

import java.util.List;

public interface DashboardService {
  List<MonthSummaryDTO> getMonthlySummaryReport(long subjectId);
}
