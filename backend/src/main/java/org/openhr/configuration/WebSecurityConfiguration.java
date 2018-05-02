package org.openhr.configuration;

import java.util.Arrays;
import java.util.List;
import org.openhr.security.RestAuthenticationEntryPoint;
import org.openhr.security.SkipPathRequestMatcher;
import org.openhr.security.filter.AjaxAuthenticationFilter;
import org.openhr.security.filter.JWTTokenAuthenticationFilter;
import org.openhr.security.handler.JWTAuthenticationFailureHandler;
import org.openhr.security.handler.JWTAuthenticationSuccessHandler;
import org.openhr.security.provider.AjaxAuthenticationProvider;
import org.openhr.security.provider.JWTAuthenticationProvider;
import org.openhr.security.service.TokenExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final String FORM_BASED_LOGIN_ENTRY_POINT = "/auth/login";
  private static final String FORM_BASED_REGISTER_ENTRY_POINT = "/employees";
  private static final String TOKEN_REFRESH_ENTRY_POINT = "/auth/token";
  private static final String STATIC_RESOURCES = "/*.{js,css,html,ico}";
  private static final String STATIC_RESOURCES_ASSETS = "/assets/*.{ico,jpg}";

  @Autowired private AuthenticationManager authenticationManager;
  private final AjaxAuthenticationProvider ajaxAuthenticationProvider;
  private final JWTAuthenticationProvider jwtAuthenticationProvider;
  private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  private final JWTAuthenticationFailureHandler jwtAuthenticationFailureHandler;
  private final JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
  private final TokenExtractorService tokenExtractorService;

  public WebSecurityConfiguration(
      final AjaxAuthenticationProvider ajaxAuthenticationProvider,
      final JWTAuthenticationProvider jwtAuthenticationProvider,
      final RestAuthenticationEntryPoint restAuthenticationEntryPoint,
      final JWTAuthenticationFailureHandler jwtAuthenticationFailureHandler,
      final JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
      final TokenExtractorService tokenExtractorService) {
    this.ajaxAuthenticationProvider = ajaxAuthenticationProvider;
    this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
    this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
    this.tokenExtractorService = tokenExtractorService;
  }

  @Override
  public void configure(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(restAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT)
        .permitAll()
        .antMatchers(HttpMethod.POST, TOKEN_REFRESH_ENTRY_POINT)
        .permitAll()
        .antMatchers(FORM_BASED_REGISTER_ENTRY_POINT)
        .permitAll()
        .antMatchers(STATIC_RESOURCES, STATIC_RESOURCES_ASSETS)
        .permitAll()
        .anyRequest()
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            buildAjaxAuthenticationFilter(FORM_BASED_LOGIN_ENTRY_POINT),
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(
            buildJWTTokenAuthenticationFilter("/**/*"), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(ajaxAuthenticationProvider);
    auth.authenticationProvider(jwtAuthenticationProvider);
  }

  private AjaxAuthenticationFilter buildAjaxAuthenticationFilter(final String loginEntryPoint) {
    final AjaxAuthenticationFilter ajaxAuthenticationFilter =
        new AjaxAuthenticationFilter(
            loginEntryPoint, jwtAuthenticationSuccessHandler, jwtAuthenticationFailureHandler);
    ajaxAuthenticationFilter.setAuthenticationManager(authenticationManager);
    return ajaxAuthenticationFilter;
  }

  private JWTTokenAuthenticationFilter buildJWTTokenAuthenticationFilter(final String pattern) {
    final List<String> pathsToSkip =
        Arrays.asList(
            TOKEN_REFRESH_ENTRY_POINT,
            FORM_BASED_LOGIN_ENTRY_POINT,
            FORM_BASED_REGISTER_ENTRY_POINT,
            STATIC_RESOURCES,
            STATIC_RESOURCES_ASSETS);
    final SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
    final JWTTokenAuthenticationFilter jwtTokenAuthenticationFilter =
        new JWTTokenAuthenticationFilter(
            matcher, jwtAuthenticationFailureHandler, tokenExtractorService);
    jwtTokenAuthenticationFilter.setAuthenticationManager(authenticationManager);
    return jwtTokenAuthenticationFilter;
  }
}
