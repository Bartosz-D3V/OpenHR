package org.openhr.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openhr.application.user.domain.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  private final AuthenticationSuccessHandler successHandler;
  private final AuthenticationFailureHandler failureHandler;

  public AjaxAuthenticationFilter(
      final String defaultFilterProcessesUrl,
      final AuthenticationSuccessHandler successHandler,
      final AuthenticationFailureHandler failureHandler) {
    super(defaultFilterProcessesUrl);
    this.successHandler = successHandler;
    this.failureHandler = failureHandler;
  }

  @Override
  public Authentication attemptAuthentication(
      final HttpServletRequest req, final HttpServletResponse res) throws AuthenticationException {
    User user;
    try {
      user = new ObjectMapper().readValue(req.getInputStream(), User.class);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
      throw new AuthenticationServiceException("Username or password is empty");
    }
    final UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

    return getAuthenticationManager().authenticate(token);
  }

  @Override
  protected void successfulAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain,
      final Authentication authResult)
      throws IOException, ServletException {
    successHandler.onAuthenticationSuccess(request, response, authResult);
  }

  @Override
  protected void unsuccessfulAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final AuthenticationException failed)
      throws IOException, ServletException {
    SecurityContextHolder.clearContext();
    failureHandler.onAuthenticationFailure(request, response, failed);
  }
}
