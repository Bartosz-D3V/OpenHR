package org.openhr.common.domain.subject;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "EMPLOYEE_INFORMATION")
public class EmployeeInformation implements Serializable {
  @Id
  @Column(name = "EMPLOYEE_INFORMATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long employeeInformationId;

  @Column(name = "NATIONAL_INSURANCE_NUMBER")
  private String nationalInsuranceNumber;
  private String position;

  @Column(name = "EMPLOYEE_ID")
  private String employeeId;

  @Column(name = "START_DATE")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;

  @Column(name = "END_DATE")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endDate;

  public EmployeeInformation() {
    super();
  }

  public EmployeeInformation(final String nationalInsuranceNumber, final String position, final String employeeId,
                             final LocalDate startDate, final LocalDate endDate) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    this.position = position;
    this.employeeId = employeeId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public long getEmployeeInformationId() {
    return employeeInformationId;
  }

  public String getNationalInsuranceNumber() {
    return nationalInsuranceNumber;
  }

  public void setNationalInsuranceNumber(final String nationalInsuranceNumber) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
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
