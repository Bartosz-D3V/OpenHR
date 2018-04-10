package org.openhr.processes.engine;

import static junit.framework.TestCase.assertNotNull;

import org.activiti.engine.ProcessEngine;
import org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessEngineTest {

  @Test
  public void processEngineWithBasicDataSource() throws Exception {
    final AnnotationConfigApplicationContext context =
        getContext(
            DataSourceAutoConfiguration.class,
            DataSourceProcessEngineAutoConfiguration.DataSourceProcessEngineConfiguration.class);

    assertNotNull("The engine should not be null!", context.getBean(ProcessEngine.class));
  }

  public static AnnotationConfigApplicationContext getContext(final Class<?>... classes) {
    final AnnotationConfigApplicationContext annotationConfigApplicationContext =
        new AnnotationConfigApplicationContext();
    annotationConfigApplicationContext.register(classes);
    annotationConfigApplicationContext.refresh();
    return annotationConfigApplicationContext;
  }
}
