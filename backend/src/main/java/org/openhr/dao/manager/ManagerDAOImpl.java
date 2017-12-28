package org.openhr.dao.manager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.Manager;
import org.openhr.exception.SubjectDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
}
