package org.openhr.security.factory;

import org.openhr.domain.user.UserContext;
import org.openhr.security.domain.JWTAccessToken;

public interface JWTTokenFactory {
  JWTAccessToken createJWTAccessToken(UserContext userContext);

  JWTAccessToken createJWTRefreshToken(UserContext userContext);
}
