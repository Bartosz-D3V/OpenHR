package org.openhr.application.dashboard.controller;

import org.openhr.application.dashboard.dto.MonthSummaryDTO;
import org.openhr.application.dashboard.dto.StatusRatioDTO;
import org.openhr.application.dashboard.facade.DashboardFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {
  private final DashboardFacade dashboardFacade;

  public DashboardController(final DashboardFacade dashboardFacade) {
    this.dashboardFacade = dashboardFacade;
  }

  @RequestMapping(value = "/{subjectId}/monthly-report", method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<MonthSummaryDTO> getMonthlySummaryReport(@PathVariable final long subjectId) {
    return dashboardFacade.getMonthlySummaryReport(subjectId);
  }

  @RequestMapping(value = "/status-ratio", method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public StatusRatioDTO getApplicationsStatusRatio() {
    return dashboardFacade.getCurrentYearStatusRatio();
  }
}
