package org.openhr.enumeration;

public enum UserPermissionRole {
  ADMIN,
  MEMBER;

  public String authority() {
    return "ROLE_" + this.name();
  }
}
