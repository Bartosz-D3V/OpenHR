package org.openhr.application.user.dto;

import java.io.Serializable;

public class PasswordDTO implements Serializable {
  private String oldPassword;
  private String newPassword;

  public PasswordDTO() {
    super();
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(final String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(final String newPassword) {
    this.newPassword = newPassword;
  }
}
