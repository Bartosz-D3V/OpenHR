package org.openhr.application.manager.dao;

import org.hibernate.SessionFactory;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public class ManagerDAOImpl extends BaseDAO implements ManagerDAO {
  public ManagerDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Set<Employee> getEmployees(final long subjectId) {
    return getManager(subjectId).getEmployees();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager addManager(final Manager manager) {
    super.save(manager);
    return manager;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager updateManager(final Manager manager) {
    final Manager legacyManager = getManager(manager.getSubjectId());
    BeanUtils.copyProperties(legacyManager, manager, "subjectId");
    super.merge(legacyManager);

    return legacyManager;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long subjectId) {
    return (Manager) super.get(Manager.class, subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addEmployeeToManager(final Manager manager, final Employee employee) {
    final Set<Employee> employees = manager.getEmployees();
    employees.add(employee);
    manager.setEmployees(employees);
    employee.setManager(manager);
    super.merge(employee);
    super.merge(manager);
  }
}
