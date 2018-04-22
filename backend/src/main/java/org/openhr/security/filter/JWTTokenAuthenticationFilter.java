package org.openhr.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openhr.security.SecurityConfigConstants;
import org.openhr.security.domain.JWTAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JWTTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  private final AuthenticationFailureHandler failureHandler;

  public JWTTokenAuthenticationFilter(
      final RequestMatcher requiresAuthenticationRequestMatcher,
      final AuthenticationFailureHandler failureHandler) {
    super(requiresAuthenticationRequestMatcher);
    this.failureHandler = failureHandler;
  }

  @Override
  public Authentication attemptAuthentication(
      final HttpServletRequest request, final HttpServletResponse response)
      throws AuthenticationException {
    final String tokenPayload = request.getHeader(SecurityConfigConstants.HEADER_STRING);
    if (tokenPayload == null) {
      throw new InsufficientAuthenticationException("Token was not provided");
    }
    final String rawToken =
        tokenPayload.substring(
            SecurityConfigConstants.TOKEN_PREFIX.length(), tokenPayload.length());
    return getAuthenticationManager().authenticate(new JWTAuthenticationToken(rawToken));
  }

  @Override
  protected void successfulAuthentication(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final FilterChain chain,
      final Authentication authResult)
      throws IOException, ServletException {
    final SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    chain.doFilter(request, response);
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
