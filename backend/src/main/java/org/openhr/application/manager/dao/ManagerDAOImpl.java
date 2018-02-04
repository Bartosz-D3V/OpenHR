package org.openhr.application.manager.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class ManagerDAOImpl implements ManagerDAO {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public ManagerDAOImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Set<Employee> getEmployees(final long managerId) throws SubjectDoesNotExistException {
    return getManager(managerId).getEmployees();
  }

  @Override
  public Manager addManager(final Manager manager) {
    try {
      final Session session = sessionFactory.openSession();
      final long generatedId = (long) session.save(manager);
      session.close();
      manager.setManagerId(generatedId);
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return manager;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateManager(final Manager manager) {
    try {
      final Session session = sessionFactory.openSession();
      final Manager legacyManager = session.get(Manager.class, manager.getManagerId());
      legacyManager.setSubject(manager.getSubject());
      legacyManager.setEmployees(manager.getEmployees());
      session.update(legacyManager);
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long managerId) throws SubjectDoesNotExistException {
    Manager manager;
    try {
      final Session session = sessionFactory.openSession();
      manager = session.get(Manager.class, managerId);
      session.close();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
    if (manager == null) {
      log.debug("Manager not found");
      throw new SubjectDoesNotExistException("Manager was not found");
    }

    return manager;
  }

  @Override
  public void addEmployeeToManager(final Employee employee, final long managerId) throws SubjectDoesNotExistException {
    final Manager manager = getManager(managerId);
    final Set<Employee> employees = manager.getEmployees();
    employees.add(employee);
    manager.setEmployees(employees);
    try {
      final Session session = sessionFactory.openSession();
      session.merge(manager);
      session.close();
    } catch (HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
  }
}