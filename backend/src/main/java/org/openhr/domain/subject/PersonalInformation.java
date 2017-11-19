package org.openhr.domain.subject;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "PERSONAL_INFORMATION")
public class PersonalInformation implements Serializable {
  @NotNull
  @OneToOne
  @JoinColumn(name = "SUBJECT_ID")
  private Subject subject;

  @Column(name = "MIDDLE_NAME")
  private String middleName;

  @Column(name = "DATE_OF_BIRTH")
  private LocalDate dateOfBirth;
  private String position;

  public PersonalInformation() {
  }

  public PersonalInformation(final Subject subject, final String middleName, final LocalDate dateOfBirth,
                             final String position) {
    this.subject = subject;
    this.middleName = middleName;
    this.dateOfBirth = dateOfBirth;
    this.position = position;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(final Subject subject) {
    this.subject = subject;
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
