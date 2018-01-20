package org.openhr.domain.authority;

import org.openhr.domain.user.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Authority implements GrantedAuthority, Serializable {
  @Id
  @Column(name = "AUTHORITY_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long authorityId;

  private final String authority;

  @ManyToMany(mappedBy = "authorities")
  private Set<User> users;

  public Authority(final String authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  public long getAuthorityId() {
    return authorityId;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(final Set<User> users) {
    this.users = users;
  }
}
