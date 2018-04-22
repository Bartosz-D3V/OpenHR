package org.openhr.security.provider;

import java.util.List;
import java.util.stream.Collectors;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserContext;
import org.openhr.application.user.service.UserService;
import org.openhr.common.exception.UserDoesNotExist;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
  private final UserService userService;

  public AjaxAuthenticationProvider(final UserService userService) {
    this.userService = userService;
  }

  @Override
  public Authentication authenticate(final Authentication authentication)
      throws AuthenticationException {
    if (authentication == null) {
      throw new InsufficientAuthenticationException("Authentication was not provided");
    }
    final String username = (String) authentication.getPrincipal();
    final String password = (String) authentication.getCredentials();
    User user;
    try {
      user = userService.getUserByUsername(username);
    } catch (final UserDoesNotExist userDoesNotExist) {
      throw new BadCredentialsException("User does not exist");
    }
    try {
      if (!userService.validCredentials(username, password)) {
        throw new BadCredentialsException("Credentials are not valid");
      }
    } catch (UserDoesNotExist userDoesNotExist) {
      userDoesNotExist.printStackTrace();
      throw new BadCredentialsException("User does not exist");
    }
    if (user.getUserRoles() == null) {
      throw new InsufficientAuthenticationException("User has no roles");
    }
    final List<GrantedAuthority> authorities =
        user.getUserRoles()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getUserRole().getRole()))
            .collect(Collectors.toList());
    final UserContext userContext = new UserContext(user.getUsername(), authorities);

    return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
