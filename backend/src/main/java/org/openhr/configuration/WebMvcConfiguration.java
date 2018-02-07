package org.openhr.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration {
  @Value("${app.turnOff.cors}")
  private boolean proxyOff;

  private static final String PATTERN_ALL_URLS = "/**/*";

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        if (!proxyOff) {
          registry.addMapping(PATTERN_ALL_URLS).allowedOrigins("http://localhost:4200");
        }
      }
    };
  }
}
