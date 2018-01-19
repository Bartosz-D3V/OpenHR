package org.openhr.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

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
}
