package org.openhr.security.factory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.openhr.application.subject.service.SubjectService;
import org.openhr.application.user.domain.UserContext;
import org.openhr.application.user.service.UserService;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.security.SecurityConfigConstants;
import org.openhr.security.domain.JWTAccessToken;
import org.openhr.security.enumeration.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JWTTokenFactoryImpl implements JWTTokenFactory {
  private final SubjectService subjectService;
  private final UserService userService;

  public JWTTokenFactoryImpl(final SubjectService subjectService,
                             final UserService userService) {
    this.subjectService = subjectService;
    this.userService = userService;
  }

  @Override
  public JWTAccessToken createJWTAccessToken(final UserContext userContext) {
    if (userContext.getUsername() == null || userContext.getUsername().isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null");
    } else if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
      throw new IllegalArgumentException("Username does not have any roles");
    }
    long subjectId;
    Subject subject;
    try {
      subjectId = userService.findSubjectId(userContext.getUsername());
      subject = subjectService.getSubjectDetails(subjectId);
    } catch (final SubjectDoesNotExistException e) {
      throw new IllegalArgumentException("User does not have a subject created");
    }
    final Claims claims = Jwts.claims().setSubject(userContext.getUsername());
    final LocalDateTime currentTime = LocalDateTime.now();
    final Map<String, Object> bodyParam = new HashMap<>();
    bodyParam.put("subjectId", subjectId);
    bodyParam.put("employeeId", subject.getEmployee().getEmployeeId());
    bodyParam.put("managerId", subject.getManager().getManagerId());
    claims.put("scopes", userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));

    final String token = Jwts.builder()
      .setClaims(claims)
      .addClaims(bodyParam)
      .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
      .setExpiration(Date.from(currentTime
        .plusMinutes(SecurityConfigConstants.EXPIRATION_TIME_IN_MINS)
        .atZone(ZoneId.systemDefault()).toInstant()))
      .signWith(SignatureAlgorithm.HS512, SecurityConfigConstants.SECRET.getBytes())
      .compact();

    return new JWTAccessToken(token, claims);
  }

  @Override
  public JWTAccessToken createJWTRefreshToken(final UserContext userContext) {
    if (userContext.getUsername() == null || userContext.getUsername().isEmpty()) {
      throw new IllegalArgumentException("Username cannot be null");
    } else if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) {
      throw new IllegalArgumentException("Username does not have any roles");
    }

    final LocalDateTime currentTime = LocalDateTime.now();
    final Claims claims = Jwts.claims().setSubject(userContext.getUsername());
    claims.put("scopes", Collections.singletonList(Scope.REFRESH_TOKEN.authority()));

    final String token = Jwts.builder()
      .setClaims(claims)
      .setId(UUID.randomUUID().toString())
      .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
      .setExpiration(Date.from(currentTime
        .plusMinutes(SecurityConfigConstants.EXPIRATION_TIME_IN_MINS)
        .atZone(ZoneId.systemDefault()).toInstant()))
      .signWith(SignatureAlgorithm.HS512, SecurityConfigConstants.SECRET.getBytes())
      .compact();

    return new JWTAccessToken(token, claims);
  }
}
