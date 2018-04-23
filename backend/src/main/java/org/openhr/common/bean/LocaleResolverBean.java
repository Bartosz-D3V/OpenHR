package org.openhr.common.bean;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class LocaleResolverBean {
  @Bean
  public LocaleResolver localeResolver() {
    final SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    sessionLocaleResolver.setDefaultLocale(Locale.UK);
    return sessionLocaleResolver;
  }
}
