package org.openhr.application.authentication.service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserContext;
import org.openhr.application.user.service.UserService;
import org.openhr.common.bean.Base64Encoder;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.security.SecurityConfigConstants;
import org.openhr.security.domain.JWTAccessToken;
import org.openhr.security.domain.RawAccessJwtToken;
import org.openhr.security.domain.RefreshToken;
import org.openhr.security.exception.InvalidJWT;
import org.openhr.security.exception.JWTExpiredException;
import org.openhr.security.factory.JWTTokenFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  private final UserService userService;
  private final JWTTokenFactory jwtTokenFactory;
  private final Base64Encoder base64Encoder;
  private final MessageSource messageSource;

  public AuthenticationServiceImpl(
      final UserService userService,
      final JWTTokenFactory jwtTokenFactory,
      final Base64Encoder base64Encoder,
      final MessageSource messageSource) {
    this.userService = userService;
    this.jwtTokenFactory = jwtTokenFactory;
    this.base64Encoder = base64Encoder;
    this.messageSource = messageSource;
  }

  @Override
  public User validateToken(final RawAccessJwtToken rawAccessJwtToken)
      throws JWTExpiredException, InvalidJWT, UserDoesNotExist {
    final String encodedKey =
        base64Encoder.getBase64Encoder().encodeToString(SecurityConfigConstants.SECRET.getBytes());
    final RefreshToken refreshToken =
        RefreshToken.create(rawAccessJwtToken, encodedKey)
            .orElseThrow(
                () ->
                    new InvalidJWT(
                        messageSource.getMessage(
                            "error.auth.invalidtoken", null, Locale.getDefault())));
    final String username = refreshToken.getSubject();
    final User user = userService.getUserByUsername(username);
    if (user.getUserRoles() == null || user.getUserRoles().size() == 0) {
      throw new IllegalArgumentException(
          messageSource.getMessage("error.userhasnoroles", null, Locale.getDefault()));
    }
    return user;
  }

  @Override
  public JWTAccessToken createToken(final User user) {
    final List<GrantedAuthority> authorities =
        user.getUserRoles()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getUserRole().getRole()))
            .collect(Collectors.toList());
    final UserContext userContext = new UserContext(user.getUsername(), authorities);
    return jwtTokenFactory.createJWTAccessToken(userContext);
  }
}
