package org.openhr.application.supervisor.service;

import java.util.List;
import org.openhr.common.domain.subject.Subject;

public interface SupervisorService {
  List<Subject> getSupervisors();
}
