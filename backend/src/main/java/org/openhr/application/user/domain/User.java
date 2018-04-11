package org.openhr.application.user.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User implements Serializable {
  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;

  @NotNull(message = "Username cannot be empty")
  @Size(max = 20, message = "Password cannot be greater than {max} characters long")
  @Column(unique = true, nullable = false)
  private String username;

  @NotNull(message = "Password cannot be empty")
  @Column(nullable = false)
  private String password;

  @Column(name = "NOTIFICATIONS")
  private boolean notificationsTurnedOn;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonBackReference
  private List<UserRole> userRoles;

  public User() {
    super();
  }

  public User(final String username, final String password) {
    super();
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

  public boolean isNotificationsTurnedOn() {
    return notificationsTurnedOn;
  }

  public void setNotificationsTurnedOn(final boolean notificationsTurnedOn) {
    this.notificationsTurnedOn = notificationsTurnedOn;
  }

  public List<UserRole> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(final List<UserRole> userRoles) {
    this.userRoles = userRoles;
  }
}
