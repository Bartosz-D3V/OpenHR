package org.openhr.security.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.List;
import java.util.Optional;
import org.openhr.security.enumeration.Scope;
import org.openhr.security.exception.JWTExpiredException;

public class RefreshToken {
  private final Jws<Claims> claims;

  private RefreshToken(final Jws<Claims> claims) {
    this.claims = claims;
  }

  @SuppressWarnings("unchecked")
  public static Optional<RefreshToken> create(
      final RawAccessJwtToken token, final String signingKey) throws JWTExpiredException {
    final Jws<Claims> claims = token.parseClaims(signingKey);

    final List<String> scopes = claims.getBody().get("scopes", List.class);
    if (scopes == null
        || scopes.isEmpty()
        || scopes.stream().noneMatch(scope -> Scope.REFRESH_TOKEN.authority().equals(scope))) {
      return Optional.empty();
    }

    return Optional.of(new RefreshToken(claims));
  }

  public String getSubject() {
    return claims.getBody().getSubject();
  }
}
