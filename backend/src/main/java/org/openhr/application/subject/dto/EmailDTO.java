package org.openhr.application.subject.dto;

import java.io.Serializable;

public class EmailDTO implements Serializable {
  private String email;

  public EmailDTO() {
    super();
  }

  public EmailDTO(final String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }
}
