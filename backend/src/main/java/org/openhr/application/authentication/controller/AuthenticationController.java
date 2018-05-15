package org.openhr.application.authentication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openhr.application.authentication.facade.AuthenticationFacade;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.security.SecurityConfigConstants;
import org.openhr.security.exception.InvalidJWT;
import org.openhr.security.exception.JWTExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
  private final AuthenticationFacade authenticationFacade;

  public AuthenticationController(final AuthenticationFacade authenticationFacade) {
    this.authenticationFacade = authenticationFacade;
  }

  @RequestMapping(value = "/token", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void refreshToken(final HttpServletRequest request, final HttpServletResponse response)
      throws JWTExpiredException, InvalidJWT, UserDoesNotExist {
    final String tokenPayload = authenticationFacade.parseToken(request);
    response.setHeader(
        SecurityConfigConstants.HEADER_STRING,
        authenticationFacade.createToken(tokenPayload).getRawToken());
  }
}
