package org.openhr.application.supervisor.service;

import java.util.List;
import org.openhr.application.supervisor.repository.SupervisorRepository;
import org.openhr.common.domain.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupervisorServiceImpl implements SupervisorService {
  private final SupervisorRepository supervisorRepository;

  public SupervisorServiceImpl(final SupervisorRepository supervisorRepository) {
    this.supervisorRepository = supervisorRepository;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Subject> getSupervisors() {
    return supervisorRepository.getSupervisors();
  }
}
