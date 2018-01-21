package org.openhr.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openhr.domain.authority.Authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class User implements Serializable {
  @Id
  @Column(name = "USER_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @JsonIgnore
  @ManyToMany
  @JoinTable(
    name = "USER_AUTHORITIES",
    joinColumns = {@JoinColumn(name = "USER_ID")},
    inverseJoinColumns = {@JoinColumn(name = "AUTHORITY_ID")}
  )
  private Set<Authority> authorities;

  public User() {
  }

  public User(final String username, final String password) {
    this.username = username;
    this.password = password;
  }

  public long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public Set<Authority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(final Set<Authority> authorities) {
    this.authorities = authorities;
  }
}
