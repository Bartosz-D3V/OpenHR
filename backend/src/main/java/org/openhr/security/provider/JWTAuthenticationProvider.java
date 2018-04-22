package org.openhr.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.openhr.application.user.domain.UserContext;
import org.openhr.security.SecurityConfigConstants;
import org.openhr.security.domain.JWTAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {
  private final Base64.Encoder base64Encoder;

  public JWTAuthenticationProvider(final Base64.Encoder base64Encoder) {
    this.base64Encoder = base64Encoder;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Authentication authenticate(final Authentication authentication)
      throws AuthenticationException {
    final String rawToken = (String) authentication.getCredentials();
    final String encodedKey =
        base64Encoder.encodeToString(SecurityConfigConstants.SECRET.getBytes());
    final Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(encodedKey).parseClaimsJws(rawToken);
    final String subject = jwsClaims.getBody().getSubject();
    final List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
    final List<GrantedAuthority> authorities =
        scopes.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    final UserContext context = new UserContext(subject, authorities);

    return new JWTAuthenticationToken(context, context.getAuthorities());
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    return (JWTAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
