package org.openhr.configuration;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
  private final ResourceProperties resourceProperties;

  public WebMvcConfiguration(final ResourceProperties resourceProperties) {
    this.resourceProperties = resourceProperties;
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(new LocaleChangeInterceptor());
    super.addInterceptors(registry);
  }

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/**")
        .addResourceLocations(resourceProperties.getStaticLocations())
        .setCachePeriod(resourceProperties.getCachePeriod())
        .resourceChain(true)
        .addResolver(new SinglePageAppResourceResolver());
  }
}
