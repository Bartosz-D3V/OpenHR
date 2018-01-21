package org.openhr.configuration;

import org.openhr.security.JWTAuthenticationFilter;
import org.openhr.security.JWTAuthorizationFilter;
import org.openhr.service.user.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;

  public WebSecurity(final UserDetailsService userDetailsService,
                     final BCryptPasswordEncoder bCryptPasswordEncoder,
                     final UserService userService) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userService = userService;
  }

  @Override
  public void configure(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf().disable().authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers(HttpMethod.POST, "/login").permitAll()
      .antMatchers(HttpMethod.POST, "/users/register").permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(new JWTAuthenticationFilter(userService))
      .addFilter(new JWTAuthorizationFilter(authenticationManager()))
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }
}
