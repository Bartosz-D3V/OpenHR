package org.openhr.application.supervisor.facade;

import java.util.List;
import org.openhr.application.supervisor.service.SupervisorService;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SupervisorFacadeImpl implements SupervisorFacade {

  private final SupervisorService supervisorService;

  public SupervisorFacadeImpl(final SupervisorService supervisorService) {
    this.supervisorService = supervisorService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Subject> getSupervisors() {
    return supervisorService.getSupervisors();
  }
}
