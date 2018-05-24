package org.openhr.configuration;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.openhr.common.factory.AutowiringSpringQuartzFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

  private final Environment environment;

  public QuartzConfiguration(final Environment environment) {
    this.environment = environment;
  }

  @Bean
  public JobFactory jobFactory(final ApplicationContext applicationContext) {
    final AutowiringSpringQuartzFactory jobFactory = new AutowiringSpringQuartzFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean(
      @Qualifier("dataSource") final DataSource dataSource, final JobFactory jobFactory)
      throws IOException {
    final SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setOverwriteExistingJobs(true);
    factory.setAutoStartup(true);
    factory.setDataSource(dataSource);
    factory.setJobFactory(jobFactory);
    factory.setQuartzProperties(quartzProperties());
    return factory;
  }

  @Bean
  public Properties quartzProperties() throws IOException {
    final PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
    propertiesFactoryBean.setLocation(getQuartzConfiguration());
    propertiesFactoryBean.afterPropertiesSet();
    return propertiesFactoryBean.getObject();
  }

  private ClassPathResource getQuartzConfiguration() {
    if (environment.getActiveProfiles()[0].equals("production")) {
      return new ClassPathResource("/quartz-prod.properties");
    }
    return new ClassPathResource("/quartz-dev.properties");
  }
}
