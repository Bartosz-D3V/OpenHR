package org.openhr.common.enumeration;

public enum Role {
  EMPLOYEE("Employee"),
  MANAGER("Manager"),
  HRTEAMMEMBER("HRTeamMember");

  private final String role;

  Role(final String role) {
    this.role = role;
  }

  public String getRole() {
    return "ROLE_" + this.name();
  }
}
