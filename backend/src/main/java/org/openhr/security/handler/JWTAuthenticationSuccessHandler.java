package org.openhr.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.openhr.application.user.domain.UserContext;
import org.openhr.security.SecurityConfigConstants;
import org.openhr.security.domain.JWTAccessToken;
import org.openhr.security.factory.JWTTokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private final JWTTokenFactory tokenFactory;
  private final ObjectMapper objectMapper;

  public JWTAuthenticationSuccessHandler(
      final JWTTokenFactory tokenFactory, final ObjectMapper objectMapper) {
    this.tokenFactory = tokenFactory;
    this.objectMapper = objectMapper;
  }

  @Override
  public void onAuthenticationSuccess(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Authentication authentication)
      throws IOException {
    final UserContext userContext = (UserContext) authentication.getPrincipal();
    final JWTAccessToken accessToken = tokenFactory.createJWTAccessToken(userContext);
    final JWTAccessToken refreshToken = tokenFactory.createJWTRefreshToken(userContext);
    final Map<String, String> responseBody = new HashMap<>();

    responseBody.put("refreshToken", refreshToken.getRawToken());
    response.setStatus(HttpStatus.OK.value());
    response.setHeader(SecurityConfigConstants.HEADER_STRING, accessToken.getRawToken());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getWriter(), responseBody);
    clearAuthenticationAttributes(request);
  }

  private void clearAuthenticationAttributes(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);
    if (!(session == null)) {
      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
  }
}
