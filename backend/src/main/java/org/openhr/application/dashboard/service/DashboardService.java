package org.openhr.application.dashboard.service;

import java.util.List;
import org.openhr.application.dashboard.dto.DelegationExpenditureDTO;
import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.dto.StatusRatioDTO;
import org.openhr.common.domain.subject.Subject;

public interface DashboardService {
  List<MonthSummaryDTO> getMonthlySummaryReport(long subjectId);

  StatusRatioDTO getCurrentYearStatusRatio();

  DelegationExpenditureDTO getDelegationExpenditures(int year);

  List<Subject> retrieveSubjectsOnLeaveToday();
}
