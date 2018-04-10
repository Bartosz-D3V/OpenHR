package org.openhr.security.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import java.io.Serializable;

public class JWTAccessToken implements Serializable {
  private final String rawToken;

  @JsonIgnore private Claims claims;

  public JWTAccessToken(final String rawToken, final Claims claims) {
    this.rawToken = rawToken;
    this.claims = claims;
  }

  public String getRawToken() {
    return rawToken;
  }

  public Claims getClaims() {
    return claims;
  }

  public void setClaims(final Claims claims) {
    this.claims = claims;
  }
}
