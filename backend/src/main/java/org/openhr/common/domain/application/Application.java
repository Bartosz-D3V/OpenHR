package org.openhr.common.domain.application;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.enumeration.ApplicationType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Application implements Serializable {
  @Id
  @Column(name = "APPLICATION_ID")
  @GenericGenerator(
    name = "APP_ID_GENERATOR",
    strategy = "enhanced-sequence",
    parameters = {
      @Parameter(name = "sequence_name", value = "APPLICATION_SEQUENCE_ID"),
      @Parameter(name = "initial_value", value = "1")
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

  @Column(name = "TYPE", updatable = false, nullable = false)
  @Enumerated(EnumType.STRING)
  protected ApplicationType applicationType;

  @Column(name = "REFUSAL_REASON")
  private String refusalReason;

  @NotNull(message = "Subject cannot be empty")
  @ManyToOne(optional = false)
  @JoinColumn(name = "APPLICANT_FK")
  @JsonProperty("subjectId")
  @JsonIdentityInfo(generator = ObjectIdGenerators.None.class, property = "subjectId")
  @JsonIdentityReference(alwaysAsId = true)
  private Subject subject;

  @ManyToOne
  @JoinColumn(name = "CURRENT_ASSIGNEE_ID")
  @JsonIgnore
  private Subject assignee;

  protected Application() {
    super();
  }

  protected Application(final LocalDate startDate, final LocalDate endDate) {
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

  public ApplicationType getApplicationType() {
    return applicationType;
  }

  public String getRefusalReason() {
    return refusalReason;
  }

  public void setRefusalReason(final String refusalReason) {
    this.refusalReason = refusalReason;
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
