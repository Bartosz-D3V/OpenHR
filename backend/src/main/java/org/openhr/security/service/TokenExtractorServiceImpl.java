package org.openhr.security.service;

import javax.servlet.http.HttpServletRequest;
import org.openhr.security.SecurityConfigConstants;
import org.springframework.stereotype.Service;

@Service
public class TokenExtractorServiceImpl implements TokenExtractorService {
  @Override
  public String extractToken(final HttpServletRequest httpServletRequest) {
    final String tokenPayload = httpServletRequest.getHeader(SecurityConfigConstants.HEADER_STRING);
    return tokenPayload.substring(
        SecurityConfigConstants.TOKEN_PREFIX.length(), tokenPayload.length());
  }
}
