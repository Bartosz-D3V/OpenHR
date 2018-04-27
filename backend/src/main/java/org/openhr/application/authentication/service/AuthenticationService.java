package org.openhr.application.authentication.service;

import org.openhr.application.user.domain.User;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.security.domain.JWTAccessToken;
import org.openhr.security.domain.RawAccessJwtToken;
import org.openhr.security.exception.InvalidJWT;
import org.openhr.security.exception.JWTExpiredException;

public interface AuthenticationService {
  User validateToken(RawAccessJwtToken rawAccessJwtToken)
      throws JWTExpiredException, InvalidJWT, UserDoesNotExist;

  JWTAccessToken createToken(User user);
}
