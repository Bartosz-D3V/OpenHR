package org.openhr.application.user.dto;

import java.io.Serializable;

public class PasswordDTO implements Serializable {
  private String oldPassword;
  private String newPassword;
  private String newPasswordRepeat;

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

  public String getNewPasswordRepeat() {
    return newPasswordRepeat;
  }

  public void setNewPasswordRepeat(final String newPasswordRepeat) {
    this.newPasswordRepeat = newPasswordRepeat;
  }
}
