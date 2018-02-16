package org.openhr.application.manager.service;

import org.openhr.application.manager.dao.ManagerDAO;
import org.openhr.application.manager.repository.ManagerRepository;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ManagerServiceImpl implements ManagerService {
  private final ManagerDAO managerDAO;
  private final ManagerRepository managerRepository;

  public ManagerServiceImpl(final ManagerDAO managerDAO,
                            final ManagerRepository managerRepository) {
    this.managerDAO = managerDAO;
    this.managerRepository = managerRepository;
  }

  @Override
  public Manager addManager(final Manager manager) {
    return managerDAO.addManager(manager);
  }

  @Override
  public void updateManager(final Manager manager) throws SubjectDoesNotExistException {
    managerDAO.updateManager(manager);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Manager> getManagers() {
    return managerRepository.getManagers();
  }

  @Override
  public Set<Employee> getEmployees(final long managerId) throws SubjectDoesNotExistException {
    return managerDAO.getEmployees(managerId);
  }

  @Override
  public void addEmployeeToManager(final Employee employee, final long managerId) throws SubjectDoesNotExistException {
    managerDAO.addEmployeeToManager(employee, managerId);
  }
}
