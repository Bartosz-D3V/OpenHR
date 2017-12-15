package org.openhr.processes;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextResolver {

  public static AnnotationConfigApplicationContext getContext(final Class<?>... classes) {
    final AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
    annotationConfigApplicationContext.register(classes);
    annotationConfigApplicationContext.refresh();
    return annotationConfigApplicationContext;
  }

}
