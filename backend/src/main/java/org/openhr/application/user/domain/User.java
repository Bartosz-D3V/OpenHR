package org.openhr.application.user.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<UserRole> userRoles;

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

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public List<UserRole> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(final List<UserRole> userRoles) {
    this.userRoles = userRoles;
  }
}
