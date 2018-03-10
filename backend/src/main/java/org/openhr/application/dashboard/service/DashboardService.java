package org.openhr.application.dashboard.service;

import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.dto.StatusRatioDTO;
import org.openhr.common.domain.subject.Subject;

import java.util.List;

public interface DashboardService {
  List<MonthSummaryDTO> getMonthlySummaryReport(long subjectId);

  StatusRatioDTO getCurrentYearStatusRatio();

  List<Subject> retrieveSubjectsOnLeaveToday();
}
