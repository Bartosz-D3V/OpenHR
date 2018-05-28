package org.openhr.application.dashboard.service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.openhr.application.dashboard.dto.DelegationExpenditureDTO;
import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.dto.StatusRatioDTO;
import org.openhr.application.dashboard.repository.DashboardRepository;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.util.date.DateRangeUtil;
import org.openhr.common.util.iterable.LocalDateRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardServiceImpl implements DashboardService {
  private final DashboardRepository dashboardRepository;

  public DashboardServiceImpl(final DashboardRepository dashboardRepository) {
    this.dashboardRepository = dashboardRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<MonthSummaryDTO> getMonthlySummaryReport(final long subjectId) {
    final List<LeaveApplication> leaveApplications =
        dashboardRepository.getApplicationsInCurrentYear(subjectId);
    return convertToMonthSummary(leaveApplications);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public StatusRatioDTO getCurrentYearStatusRatio() {
    final List<LeaveApplication> leaveApplications =
        dashboardRepository.getCurrentYearStatusRatio();
    return convertToStatusRatio(leaveApplications);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public DelegationExpenditureDTO getDelegationExpenditures(final int year) {
    final BigDecimal totalExpenditures =
        dashboardRepository.getDelegationExpenditures(year) != null
            ? dashboardRepository.getDelegationExpenditures(year)
            : new BigDecimal(0);
    final DelegationExpenditureDTO delegationExpenditureDTO = new DelegationExpenditureDTO();
    delegationExpenditureDTO.setYear(year);
    delegationExpenditureDTO.setTotalExpenditure(totalExpenditures);
    return delegationExpenditureDTO;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Subject> retrieveSubjectsOnLeaveToday() {
    return dashboardRepository.retrieveSubjectsOnLeaveToday();
  }

  private List<MonthSummaryDTO> convertToMonthSummary(
      final List<LeaveApplication> leaveApplications) {
    final List<MonthSummaryDTO> result = new ArrayList<>();
    for (final Month month : Month.values()) {
      final MonthSummaryDTO monthSummaryDTO = new MonthSummaryDTO();
      final long monthCounter =
          leaveApplications
              .stream()
              .filter(
                  leaveApplication ->
                      DateRangeUtil.monthInRange(
                          new LocalDateRange(
                              leaveApplication.getStartDate(), leaveApplication.getEndDate()),
                          month))
              .mapToLong(
                  leaveApplication ->
                      DateRangeUtil.diffDaysInMonth(
                          leaveApplication.getStartDate(), leaveApplication.getEndDate(), month))
              .sum();
      monthSummaryDTO.setMonth(month);
      monthSummaryDTO.setNumberOfApplications(monthCounter);
      result.add(monthSummaryDTO);
    }
    return result;
  }

  private StatusRatioDTO convertToStatusRatio(final List<LeaveApplication> leaveApplications) {
    final long numOfAcceptedApplications =
        leaveApplications.stream().filter(LeaveApplication::isApprovedByHR).count();
    final long numOfRejectedApplications = leaveApplications.size() - numOfAcceptedApplications;
    final StatusRatioDTO statusRatioDTO = new StatusRatioDTO();
    statusRatioDTO.setAccepted(numOfAcceptedApplications);
    statusRatioDTO.setRejected(numOfRejectedApplications);
    return statusRatioDTO;
  }
}
