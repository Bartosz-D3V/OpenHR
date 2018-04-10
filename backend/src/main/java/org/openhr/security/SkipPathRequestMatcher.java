package org.openhr.security;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher {
  private final OrRequestMatcher matchers;
  private final RequestMatcher processingMatcher;

  public SkipPathRequestMatcher(final List<String> pathsToSkip, final String processingPath) {
    if (pathsToSkip == null) {
      throw new IllegalArgumentException("List of paths cannot be null");
    }
    final List<RequestMatcher> requestMatchers =
        pathsToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
    matchers = new OrRequestMatcher(requestMatchers);
    processingMatcher = new AntPathRequestMatcher(processingPath);
  }

  @Override
  public boolean matches(final HttpServletRequest request) {
    return !matchers.matches(request) && processingMatcher.matches(request);
  }
}
