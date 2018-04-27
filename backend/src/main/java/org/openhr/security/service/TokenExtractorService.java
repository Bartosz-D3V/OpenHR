package org.openhr.security.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenExtractorService {
  String extractToken(HttpServletRequest httpServletRequest);
}
