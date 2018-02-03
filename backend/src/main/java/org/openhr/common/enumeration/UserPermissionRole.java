package org.openhr.common.enumeration;

public enum UserPermissionRole {
  ADMIN,
  MEMBER;

  public String authority() {
    return "ROLE_" + this.name();
  }
}
