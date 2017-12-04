package org.openhr.domain.subject;

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
@Table(name = "PERSONAL_INFORMATION")
public class PersonalInformation implements Serializable {
  @Id
  @Column(name = "PERSONAL_INFORMATION_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long personalInformationId;

  @Column(name = "MIDDLE_NAME")
  private String middleName;

  @Column(name = "DATE_OF_BIRTH")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  public PersonalInformation() {
    super();
  }

  public PersonalInformation(final String middleName, final LocalDate dateOfBirth) {
    this.middleName = middleName;
    this.dateOfBirth = dateOfBirth;
  }

  public long getPersonalInformationId() {
    return personalInformationId;
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
