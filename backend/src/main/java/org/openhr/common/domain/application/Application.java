package org.openhr.common.domain.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.openhr.common.domain.subject.Subject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Application implements Serializable {
  @Id
  @Column(name = "APPLICATION_ID")
  @GenericGenerator(
    name = "APP_ID_GENERATOR",
    strategy = "enhanced-sequence",
    parameters = {
      @Parameter(
        name = "sequence_name",
        value = "APPLICATION_SEQUENCE_ID"
      ),
      @Parameter(
        name = "initial_value",
        value = "1"
      )
    }
  )
  @GeneratedValue(generator = "APP_ID_GENERATOR")
  private long applicationId;

  @NotNull(message = "Start date cannot be empty")
  @Column(name = "START_DATE")
  private LocalDate startDate;

  @NotNull(message = "End date cannot be empty")
  @Column(name = "END_DATE")
  private LocalDate endDate;

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

  public Application() {
    super();
  }

  public Application(final LocalDate startDate, final LocalDate endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public long getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(final long applicationId) {
    this.applicationId = applicationId;
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

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof Application)) return false;

    final Application that = (Application) o;

    return getApplicationId() == that.getApplicationId() && isApprovedByManager() == that.isApprovedByManager() &&
      isApprovedByHR() == that.isApprovedByHR() && isTerminated() == that.isTerminated() &&
      (getStartDate() != null ? getStartDate().equals(that.getStartDate()) :
        that.getStartDate() == null) && (getEndDate() != null ? getEndDate().equals(that.getEndDate()) :
      that.getEndDate() == null) && (getProcessInstanceId() != null ?
      getProcessInstanceId().equals(that.getProcessInstanceId()) :
      that.getProcessInstanceId() == null) && getSubject().equals(that.getSubject()) && (getAssignee() != null ?
      getAssignee().equals(that.getAssignee()) :
      that.getAssignee() == null);
  }
}
