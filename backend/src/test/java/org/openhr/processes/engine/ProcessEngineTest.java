package org.openhr.processes.engine;

import org.activiti.engine.ProcessEngine;
import org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessEngineTest {

  @Test
  public void processEngineWithBasicDataSource() throws Exception {
    final AnnotationConfigApplicationContext context = this.context(
      DataSourceAutoConfiguration.class,
      DataSourceProcessEngineAutoConfiguration.DataSourceProcessEngineConfiguration.class);
    assertNotNull("The engine should not be null!", context.getBean(ProcessEngine.class));
  }

  private AnnotationConfigApplicationContext context(final Class<?>... classes) {
    final AnnotationConfigApplicationContext annotationConfigApplicationContext =
      new AnnotationConfigApplicationContext();
    annotationConfigApplicationContext.register(classes);
    annotationConfigApplicationContext.refresh();
    return annotationConfigApplicationContext;
  }

}
