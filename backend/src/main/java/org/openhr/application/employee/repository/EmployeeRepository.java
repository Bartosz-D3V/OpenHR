package org.openhr.application.employee.repository;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.application.employee.dao.EmployeeDAO;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmployeeRepository {
  private final EmployeeDAO employeeDAO;
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public EmployeeRepository(final EmployeeDAO employeeDAO, final SessionFactory sessionFactory) {
    this.employeeDAO = employeeDAO;
    this.sessionFactory = sessionFactory;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  @SuppressWarnings("unchecked")
  public List<Employee> getEmployees() {
    List<Employee> employees;
    try {
      final Session session = sessionFactory.getCurrentSession();
      final Criteria criteria = session.createCriteria(Employee.class);
      employees = (List<Employee>) criteria.list();
      session.flush();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    return employees;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Employee getEmployee(final long subjectId) {
    return employeeDAO.getEmployee(subjectId);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Employee createEmployee(final Employee employee) {
    return employeeDAO.createEmployee(employee);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    return employeeDAO.updateEmployee(subjectId, employee);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteEmployee(final Employee employee) {
    employeeDAO.deleteEmployee(employee);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Manager setManagerToEmployee(final long employeeId, final Manager manager) {
    return employeeDAO.setManagerToEmployee(employeeId, manager);
  }
}
