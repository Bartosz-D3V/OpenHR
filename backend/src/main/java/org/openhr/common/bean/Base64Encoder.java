package org.openhr.common.bean;

import java.util.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Base64Encoder {
  @Bean
  public Base64.Encoder getBase64Encoder() {
    return java.util.Base64.getEncoder();
  }
}
