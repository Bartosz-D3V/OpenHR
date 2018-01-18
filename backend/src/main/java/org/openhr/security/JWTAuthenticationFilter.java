package org.openhr.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.openhr.domain.user.User;
import org.openhr.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.openhr.security.SecurityConstants.EXPIRATION_TIME_IN_MILIS;
import static org.openhr.security.SecurityConstants.HEADER_STRING;
import static org.openhr.security.SecurityConstants.SECRET;
import static org.openhr.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements AuthenticationManager {
  private final UserService userService;

  public JWTAuthenticationFilter(final UserService userService) {
    this.userService = userService;
  }

  @Override
  public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
    throws AuthenticationException {
    try {
      final User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
      final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
        user.getPassword(), new ArrayList<>());
      return authenticate(token);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                          final FilterChain chain, final Authentication authResult) {
    final String token = Jwts.builder()
      .setSubject((String) authResult.getPrincipal())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILIS))
      .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
      .compact();
    response.addHeader(HEADER_STRING, TOKEN_PREFIX.concat(token));
  }

  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
    final String username = authentication.getName();
    final String password = authentication.getCredentials().toString();
    final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    if (!userService.validCredentials(username, password)) {
      throw new BadCredentialsException("Bad credentials");
    }
    return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
  }
}
