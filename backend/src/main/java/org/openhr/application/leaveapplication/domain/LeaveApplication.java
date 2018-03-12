package org.openhr.application.leaveapplication.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openhr.common.domain.subject.Subject;

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
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "LEAVE_APPLICATION")
public class LeaveApplication implements Serializable {
  @Id
  @Column(name = "APPLICATION_ID", unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long applicationId;

  @NotNull(message = "Start date cannot be empty")
  @Column(name = "START_DATE")
  private LocalDate startDate;

  @NotNull(message = "End date cannot be empty")
  @Column(name = "END_DATE")
  private LocalDate endDate;

  @Size(max = 500, message = "Message cannot be greater than {max} characters long")
  @Column(length = 500)
  private String message;

  @NotNull(message = "Leave type cannot be empty")
  @JoinColumn(name = "LEAVE_TYPE_LEAVE_TYPE_ID")
  @ManyToOne(optional = false)
  private LeaveType leaveType;

  @Column(name = "APPROVED_BY_MANAGER")
  private boolean approvedByManager;

  @Column(name = "APPROVED_BY_HR")
  private boolean approvedByHR;

  @Column(name = "APPLICATION_TERMINATED")
  private boolean terminated;

  @Column(name = "PROCESS_INSTANCE_ID")
  private String processInstanceId;

  @NotNull(message = "Subject cannot be empty")
  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "APPLICANT_ID")
  private Subject subject;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "CURRENT_ASSIGNEE_ID")
  @JsonIgnore
  private Subject assignee;

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

  public void setApplicationId(final long applicationId) {
    this.applicationId = applicationId;
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

  public LeaveType getLeaveType() {
    return leaveType;
  }

  public void setLeaveType(final LeaveType leaveType) {
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

  public boolean isTerminated() {
    return terminated;
  }

  public void setTerminated(final boolean terminated) {
    this.terminated = terminated;
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

  public Subject getAssignee() {
    return assignee;
  }

  public void setAssignee(final Subject assignee) {
    this.assignee = assignee;
  }
}
