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
  private long UserId;

  private String email;
  private String password;

  public User() {
  }

  public User(final String email, final String password) {
    this.email = email;
    this.password = password;
  }

  public long getUserId() {
    return UserId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }
}
