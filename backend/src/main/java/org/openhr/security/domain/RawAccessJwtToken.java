package org.openhr.security.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Locale;
import org.openhr.security.exception.JWTExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;

public class RawAccessJwtToken {
  private final String token;
  private final MessageSource messageSource;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public RawAccessJwtToken(final String token, @Lazy final MessageSource messageSource) {
    this.token = token;
    this.messageSource = messageSource;
  }

  Jws<Claims> parseClaims(final String signingKey) throws JWTExpiredException {
    try {
      return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
    } catch (UnsupportedJwtException
        | MalformedJwtException
        | IllegalArgumentException
        | SignatureException ex) {
      log.error(messageSource.getMessage("error.auth.invalidtoken", null, Locale.getDefault()), ex);
      throw new BadCredentialsException("Invalid JWT token: ", ex);
    } catch (final ExpiredJwtException expiredEx) {
      log.info(messageSource.getMessage("error.auth.tokenexpired", null, Locale.getDefault()));
      throw new JWTExpiredException(
          messageSource.getMessage("error.auth.tokenexpired", null, Locale.getDefault()));
    }
  }
}
