package org.openhr.domain.subject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "EMPLOYEE_INFORMATION")
public class EmployeeInformation implements Serializable {
  @Id
  @NotNull
  @OneToOne
  @JsonIgnore
  @JoinColumn(name = "SUBJECT_ID")
  private Subject subject;

  @Column(name = "NATIONAL_INSURANCE_NUMBER")
  private String nationalInsuranceNumber;

  @Column(name = "EMPLOYEE_ID")
  private String employeeId;

  @Column(name = "START_DATE")
  private LocalDate startDate;

  @Column(name = "END_DATE")
  private LocalDate endDate;

  public EmployeeInformation() {
    super();
  }

  public EmployeeInformation(final String nationalInsuranceNumber, final String employeeId, final LocalDate startDate,
                             final LocalDate endDate) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    this.employeeId = employeeId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
  }

  public String getNationalInsuranceNumber() {
    return nationalInsuranceNumber;
  }

  public void setNationalInsuranceNumber(final String nationalInsuranceNumber) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
  }

  public String getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(final String employeeId) {
    this.employeeId = employeeId;
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
}
