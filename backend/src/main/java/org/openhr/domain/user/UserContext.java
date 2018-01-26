package org.openhr.domain.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserContext {
  private final String username;
  private final List<GrantedAuthority> authorities;

  public UserContext(final String username, final List<GrantedAuthority> authorities) {
    this.username = username;
    this.authorities = authorities;
  }

  public String getUsername() {
    return username;
  }

  public List<GrantedAuthority> getAuthorities() {
    return authorities;
  }
}
