package org.openhr.security.enumeration;

public enum Scope {
  REFRESH_TOKEN;

  public String authority() {
    return "ROLE_" + this.name();
  }
}
