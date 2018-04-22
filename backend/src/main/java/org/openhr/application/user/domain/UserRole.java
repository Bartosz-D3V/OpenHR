package org.openhr.application.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.openhr.common.enumeration.Role;

@Entity
@Table(name = "USER_ROLE")
public class UserRole {
  @Id
  @Column(name = "USER_ROLE_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userRoleId;

  @Enumerated(EnumType.STRING)
  @Column(name = "USER_ROLE")
  private Role role;

  @ManyToOne
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  public UserRole() {
    super();
  }

  public UserRole(final Role role) {
    this.role = role;
  }

  public long getUserRoleId() {
    return userRoleId;
  }

  public Role getUserRole() {
    return role;
  }

  public void setUserRole(final Role role) {
    this.role = role;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
