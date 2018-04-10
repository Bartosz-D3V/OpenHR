package org.openhr.common.domain.subject;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PERSONAL_INFORMATION")
public class PersonalInformation implements Serializable {
  @Id
  @Column(name = "PERSONAL_INFORMATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long personalInformationId;

  @NotNull(message = "First name cannot be empty")
  @Size(max = 255, message = "First name cannot be greater than {max} characters long")
  @Column(name = "FIRST_NAME")
  private String firstName;

  @NotNull(message = "Last name cannot be empty")
  @Size(max = 255, message = "Last name cannot be greater than {max} characters long")
  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "MIDDLE_NAME")
  @Size(max = 255)
  private String middleName;

  @Column(name = "DATE_OF_BIRTH")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  public PersonalInformation() {
    super();
  }

  public PersonalInformation(
      final String firstName,
      final String lastName,
      final String middleName,
      final LocalDate dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.dateOfBirth = dateOfBirth;
  }

  public long getPersonalInformationId() {
    return personalInformationId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(final String middleName) {
    this.middleName = middleName;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(final LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
