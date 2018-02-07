package org.openhr.application.manager.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public class ManagerDAOImpl extends BaseDAO implements ManagerDAO {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public ManagerDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Set<Employee> getEmployees(final long managerId) throws SubjectDoesNotExistException {
    return getManager(managerId).getEmployees();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Manager addManager(final Manager manager) {
    try {
      final Session session = sessionFactory.openSession();
      final long generatedId = (long) session.save(manager);
      session.close();
      manager.setManagerId(generatedId);
      super.merge(manager);
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return manager;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void updateManager(final Manager manager) throws SubjectDoesNotExistException {
    final Manager legacyManager = getManager(manager.getManagerId());
    legacyManager.setSubject(manager.getSubject());
    legacyManager.setEmployees(manager.getEmployees());
    super.merge(legacyManager);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long managerId) throws SubjectDoesNotExistException {
    return (Manager) super.get(Manager.class, managerId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void addEmployeeToManager(final Employee employee, final long managerId) throws SubjectDoesNotExistException {
    final Manager manager = getManager(managerId);
    final Set<Employee> employees = manager.getEmployees();
    employees.add(employee);
    manager.setEmployees(employees);
    super.merge(manager);
  }
}
