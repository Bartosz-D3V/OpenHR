package org.openhr.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.openhr.domain.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.openhr.security.SecurityConstants.EXPIRATION_TIME_IN_MILIS;
import static org.openhr.security.SecurityConstants.HEADER_STRING;
import static org.openhr.security.SecurityConstants.SECRET;
import static org.openhr.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(final AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(final HttpServletRequest req, final HttpServletResponse res)
    throws AuthenticationException {
    try {
      final User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
      final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
        user.getPassword(), new ArrayList<>());
      return authenticationManager.authenticate(token);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                          final FilterChain chain, final Authentication authResult)
    throws IOException, ServletException {
    final String token = Jwts.builder()
      .setSubject(((User) authResult.getPrincipal()).getEmail())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_IN_MILIS))
      .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
      .compact();
    response.addHeader(HEADER_STRING, TOKEN_PREFIX.concat(token));
  }
}
