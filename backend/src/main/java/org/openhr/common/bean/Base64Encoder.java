package org.openhr.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class Base64Encoder {
  @Bean
  public Base64.Encoder getBase64Encoder() {
    return java.util.Base64.getEncoder();
  }
}
