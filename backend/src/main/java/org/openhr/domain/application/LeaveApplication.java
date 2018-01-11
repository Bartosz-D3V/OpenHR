package org.openhr.domain.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openhr.domain.subject.Subject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "LEAVE_APPLICATION")
public class LeaveApplication implements Serializable {
  @Id
  @Column(name = "APPLICATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @Column(name = "PROCESS_INSTANCE_ID")
  private String processInstanceId;

  @NotNull
  @JsonIgnore
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "SUBJECT_ID", nullable = false)
  private Subject subject;

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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(final LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(final LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public String getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(final String leaveType) {
    this.leaveType = leaveType;
  }

  public boolean isApprovedByManager() {
    return approvedByManager;
  }

  public void setApprovedByManager(final boolean approvedByManager) {
    this.approvedByManager = approvedByManager;
  }

  public boolean isApprovedByHR() {
    return approvedByHR;
  }

  public void setApprovedByHR(final boolean approvedByHR) {
    this.approvedByHR = approvedByHR;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(final String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
  }
}
