package org.openhr.domain.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "LEAVE_APPLICATION")
public class LeaveApplication implements Serializable {
  @Id
  @Column(name = "APPLICATION_ID")
  @GeneratedValue
  private long applicationId;

  @Column(name = "START_DATE")
  private LocalDate startDate;

  @Column(name = "END_DATE")
  private LocalDate endDate;
  private String message;

  @Column(name = "LEAVE_TYPE")
  private String leaveType;

  @Column(name = "APPROVED_BY_MANAGER")
  private boolean approvedByManager;

  @Column(name = "APPROVED_BY_HR")
  private boolean approvedByHR;

  public LeaveApplication() {
    super();
  }

  public LeaveApplication(final LocalDate startDate, final LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public LeaveApplication(final LocalDate startDate, final LocalDate endDate, final String message) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.message = message;
  }

  public long getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(long applicationId) {
    this.applicationId = applicationId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(String leaveType) {
    this.leaveType = leaveType;
  }

  public boolean isApprovedByManager() {
    return approvedByManager;
  }

  public void setApprovedByManager(boolean approvedByManager) {
    this.approvedByManager = approvedByManager;
  }

  public boolean isApprovedByHR() {
    return approvedByHR;
  }

  public void setApprovedByHR(boolean approvedByHR) {
    this.approvedByHR = approvedByHR;
  }
}
