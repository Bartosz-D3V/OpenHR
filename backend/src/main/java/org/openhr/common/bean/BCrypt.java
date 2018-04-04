package org.openhr.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCrypt {
  @Bean
  public BCryptPasswordEncoder BCryptPasswordEncoder() {
    return new BCryptPasswordEncoder(8);
  }
}
