package org.openhr.application.dashboard.dto;

import java.io.Serializable;
import java.time.Month;

public class MonthSummaryDTO implements Serializable {
  private Month month;
  private long numberOfApplications;

  public MonthSummaryDTO() {
    super();
  }

  public Month getMonth() {
    return month;
  }

  public void setMonth(final Month month) {
    this.month = month;
  }

  public long getNumberOfApplications() {
    return numberOfApplications;
  }

  public void setNumberOfApplications(final long numberOfApplications) {
    this.numberOfApplications = numberOfApplications;
  }
}
