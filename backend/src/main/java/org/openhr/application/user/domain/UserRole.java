package org.openhr.application.user.domain;

import org.openhr.common.enumeration.UserPermissionRole;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "USER_ROLE")
public class UserRole {
  @Id
  @Column(name = "USER_ROLE_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userRoleId;

  @Enumerated(EnumType.STRING)
  @Column(name = "USER_PERMISSION_ROLE")
  private UserPermissionRole userPermissionRole;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  public UserRole() {
  }

  public UserRole(final UserPermissionRole userPermissionRole) {
    this.userPermissionRole = userPermissionRole;
  }

  public long getUserRoleId() {
    return userRoleId;
  }

  public UserPermissionRole getUserPermissionRole() {
    return userPermissionRole;
  }

  public void setUserPermissionRole(final UserPermissionRole userPermissionRole) {
    this.userPermissionRole = userPermissionRole;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
