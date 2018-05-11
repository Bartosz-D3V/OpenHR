package org.openhr.application.user.dto;

import java.io.Serializable;

public class PasswordDTO implements Serializable {
  private String oldPassword;
  private String password;

  public PasswordDTO() {
    super();
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(final String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
