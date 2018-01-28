package org.openhr.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openhr.domain.user.UserContext;
import org.openhr.security.domain.JWTAccessToken;
import org.openhr.security.factory.JWTTokenFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
  private final ObjectMapper mapper = new ObjectMapper();
  private final JWTTokenFactory tokenFactory;

  public JWTAuthenticationSuccessHandler(final JWTTokenFactory tokenFactory) {
    this.tokenFactory = tokenFactory;
  }

  @Override
  public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                      final Authentication authentication) throws IOException {
    final UserContext userContext = (UserContext) authentication.getPrincipal();
    final JWTAccessToken accessToken = tokenFactory.createJWTAccessToken(userContext);
    final JWTAccessToken refreshToken = tokenFactory.createJWTAccessToken(userContext);
    final Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", accessToken.getRawToken());
    tokenMap.put("refreshToken", refreshToken.getRawToken());

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    mapper.writeValue(response.getWriter(), tokenMap);
    clearAuthenticationAttributes(request);
  }

  private void clearAuthenticationAttributes(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);
    if (!(session == null)) {
      session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
  }
}