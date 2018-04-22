package org.openhr.application.subject.dto;

import java.io.Serializable;

public class LightweightSubjectDTO implements Serializable {
  private long subjectId;
  private String firstName;
  private String lastName;
  private String position;

  public LightweightSubjectDTO() {
    super();
  }

  public long getSubjectId() {
    return subjectId;
  }

  public void setSubjectId(final long subjectId) {
    this.subjectId = subjectId;
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

  public String getPosition() {
    return position;
  }

  public void setPosition(final String position) {
    this.position = position;
  }
}
