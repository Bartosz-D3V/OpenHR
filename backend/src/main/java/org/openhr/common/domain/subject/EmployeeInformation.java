package org.openhr.common.domain.subject;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "EMPLOYEE_INFORMATION")
public class EmployeeInformation implements Serializable {
  @Id
  @Column(name = "EMPLOYEE_INFORMATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long employeeInformationId;

  @Column(name = "NATIONAL_INSURANCE_NUMBER")
  @Size(max = 13)
  private String nationalInsuranceNumber;

  @Size(max = 255)
  private String position;

  @Size(max = 255)
  private String department;

  @Column(name = "EMPLOYEE_NUMBER")
  @Size(max = 255)
  private String employeeNumber;

  @Column(name = "START_DATE")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate startDate;

  @Column(name = "END_DATE")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate endDate;

  public EmployeeInformation() {
    super();
  }

  public EmployeeInformation(
      final String nationalInsuranceNumber,
      final String position,
      final String department,
      final String employeeNumber,
      final LocalDate startDate,
      final LocalDate endDate) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    this.position = position;
    this.department = department;
    this.employeeNumber = employeeNumber;
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

  public String getDepartment() {
    return department;
  }

  public void setDepartment(final String department) {
    this.department = department;
  }

  public String getEmployeeNumber() {
    return employeeNumber;
  }

  public void setEmployeeNumber(final String employeeNumber) {
    this.employeeNumber = employeeNumber;
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
