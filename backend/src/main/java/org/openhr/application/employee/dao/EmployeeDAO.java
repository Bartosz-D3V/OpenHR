package org.openhr.application.employee.dao;

import org.hibernate.SessionFactory;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmployeeDAO extends BaseDAO {
  public EmployeeDAO(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Employee getEmployee(final long subjectId) {
    return (Employee) super.get(Employee.class, subjectId);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee createEmployee(final Employee employee) {
    super.save(employee);
    return employee;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager setManagerToEmployee(final long subjectId, final Employee employee) {
    final Manager savedManager = (Manager) super.get(Manager.class, subjectId);
    employee.setManager(savedManager);
    super.merge(savedManager);

    return savedManager;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    Employee savedEmployee = getEmployee(subjectId);
    BeanUtils.copyProperties(savedEmployee, employee);
    super.merge(savedEmployee);

    return savedEmployee;
  }
}
