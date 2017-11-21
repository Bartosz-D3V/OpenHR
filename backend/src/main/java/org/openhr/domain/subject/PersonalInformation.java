package org.openhr.domain.subject;

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
  private LocalDate dateOfBirth;
  private String position;

  public PersonalInformation() {
    super();
  }

  public PersonalInformation(final String middleName, final LocalDate dateOfBirth, final String position) {
    this.middleName = middleName;
    this.dateOfBirth = dateOfBirth;
    this.position = position;
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

  public String getPosition() {
    return position;
  }

  public void setPosition(final String position) {
    this.position = position;
  }
}
