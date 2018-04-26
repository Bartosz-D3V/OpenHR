package org.openhr.application.authentication.facade;

import javax.servlet.http.HttpServletRequest;
import org.openhr.application.authentication.service.AuthenticationService;
import org.openhr.application.user.domain.User;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.security.domain.JWTAccessToken;
import org.openhr.security.domain.RawAccessJwtToken;
import org.openhr.security.exception.InvalidJWT;
import org.openhr.security.exception.JWTExpiredException;
import org.openhr.security.service.TokenExtractorService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {
  private final TokenExtractorService tokenExtractorService;
  private final AuthenticationService authenticationService;
  private final MessageSource messageSource;

  public AuthenticationFacadeImpl(
      final TokenExtractorService tokenExtractorService,
      final AuthenticationService authenticationService,
      final MessageSource messageSource) {
    this.tokenExtractorService = tokenExtractorService;
    this.authenticationService = authenticationService;
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public String parseToken(final HttpServletRequest request) {
    return tokenExtractorService.extractToken(request);
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public User validateToken(final RawAccessJwtToken rawAccessJwtToken)
      throws JWTExpiredException, InvalidJWT, UserDoesNotExist {
    return authenticationService.validateToken(rawAccessJwtToken);
  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public JWTAccessToken createToken(final String tokenPayload)
      throws JWTExpiredException, UserDoesNotExist, InvalidJWT {
    final User user = validateToken(new RawAccessJwtToken(tokenPayload, messageSource));
    return authenticationService.createToken(user);
  }
}
