package org.openhr.application.employee.dao;

import org.hibernate.SessionFactory;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDAO extends BaseDAO {
  public EmployeeDAO(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee createEmployee(final Employee employee) {
    super.save(employee);
    return employee;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setEmployeeManager(final long subjectId, final Employee employee) {
    final Manager savedManager = (Manager) super.get(Manager.class, subjectId);
    employee.setManager(savedManager);
    super.merge(savedManager);

    return savedManager;
  }
}
