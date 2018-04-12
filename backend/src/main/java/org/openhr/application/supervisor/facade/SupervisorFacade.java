package org.openhr.application.supervisor.facade;

import java.util.List;
import org.openhr.common.domain.subject.Subject;

public interface SupervisorFacade {
  List<Subject> getSupervisors();
}
