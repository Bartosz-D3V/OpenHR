package org.openhr.security.domain;

import java.io.Serializable;
import java.util.Collection;
import org.openhr.application.user.domain.UserContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JWTAuthenticationToken extends AbstractAuthenticationToken implements Serializable {
  private String rawAccessToken;
  private UserContext userContext;

  public JWTAuthenticationToken(
      final UserContext userContext, final Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.eraseCredentials();
    this.userContext = userContext;
    super.setAuthenticated(true);
  }

  public JWTAuthenticationToken(final String unsafeToken) {
    super(null);
    this.rawAccessToken = unsafeToken;
    this.setAuthenticated(false);
  }

  @Override
  public Object getCredentials() {
    return rawAccessToken;
  }

  @Override
  public Object getPrincipal() {
    return userContext;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    rawAccessToken = null;
  }
}
