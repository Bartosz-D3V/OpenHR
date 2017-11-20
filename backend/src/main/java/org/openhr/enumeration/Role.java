package org.openhr.enumeration;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum Role {
  EMPLOYEE("Employee"),
  MANAGER("Manager"),
  HRTEAMMEMBER("HR Team Member");

  private String role;

  Role(final String role) {
    this.role = role;
  }

  @Enumerated(EnumType.STRING)
  public String role() {
    return role;
  }

}
