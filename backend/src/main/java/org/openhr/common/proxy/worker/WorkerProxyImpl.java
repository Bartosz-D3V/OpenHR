package org.openhr.common.proxy.worker;

import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.service.EmployeeService;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.hr.service.HrService;
import org.openhr.application.manager.domain.Manager;
import org.openhr.application.manager.service.ManagerService;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class WorkerProxyImpl implements WorkerProxy {
  private final EmployeeService employeeService;
  private final ManagerService managerService;
  private final HrService hrService;

  public WorkerProxyImpl(
      @Lazy final EmployeeService employeeService,
      @Lazy final ManagerService managerService,
      @Lazy final HrService hrService) {
    this.employeeService = employeeService;
    this.managerService = managerService;
    this.hrService = hrService;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Employee getEmployee(final long employeeId) {
    return employeeService.getEmployee(employeeId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long managerId) throws SubjectDoesNotExistException {
    return managerService.getManager(managerId);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long hrTeamMemberId) {
    return hrService.getHrTeamMember(hrTeamMemberId);
  }
}
