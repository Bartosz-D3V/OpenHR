package org.openhr.domain.application;

import org.openhr.enumeration.LeaveType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "LEAVE_APPLICATION")
public class LeaveApplication implements Serializable {
  @Id
  @Column(name = "APPLICATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long applicationId;

  @Column(name = "SUBJECT_ID")
  private long subjectId;

  @Column(name = "START_DATE")
  private LocalDate startDate;

  @Column(name = "END_DATE")
  private LocalDate endDate;
  private String message;

  @Column(name = "LEAVE_TYPE")
  private LeaveType leaveType;

  @Column(name = "APPROVED_BY_MANAGER")
  private boolean approvedByManager;

  @Column(name = "APPROVED_BY_HR")
  private boolean approvedByHR;

  public LeaveApplication() {
  }

  public LeaveApplication(long subjectId, LocalDate startDate, LocalDate endDate) {
    this.subjectId = subjectId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public LeaveApplication(long subjectId, LocalDate startDate, LocalDate endDate, String message,
                          boolean approvedByManager, boolean approvedByHR) {
    this.subjectId = subjectId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.message = message;
    this.approvedByManager = approvedByManager;
    this.approvedByHR = approvedByHR;
  }

  public long getApplicationId() {
    return applicationId;
  }

  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(long subjectId) {
    this.subjectId = subjectId;
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
