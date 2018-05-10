package org.openhr.application.manager.repository;

import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.dao.ManagerDAO;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ManagerRepository {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final SessionFactory sessionFactory;
  private final ManagerDAO managerDAO;

  public ManagerRepository(final SessionFactory sessionFactory, final ManagerDAO managerDAO) {
    this.sessionFactory = sessionFactory;
    this.managerDAO = managerDAO;
  }

  @SuppressWarnings("unchecked")
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public List<Manager> getManagers() {
    List<Manager> managers;
    try {
      final Session session = sessionFactory.getCurrentSession();
      managers = session.createCriteria(Manager.class).setReadOnly(true).list();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return managers;
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long subjectId) throws SubjectDoesNotExistException {
    return managerDAO.getManager(subjectId);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Manager addManager(final Manager manager) {
    return managerDAO.addManager(manager);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Manager updateManager(final long subjectId, final Manager manager)
      throws SubjectDoesNotExistException {
    return managerDAO.updateManager(subjectId, manager);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteManager(final Manager manager) {
    managerDAO.deleteManager(manager);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public Set<Employee> getManagersEmployees(final long subjectId)
      throws SubjectDoesNotExistException {
    return managerDAO.getManagersEmployees(subjectId);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void addEmployeeToManager(final Manager manager, final Employee employee)
      throws SubjectDoesNotExistException {
    managerDAO.addEmployeeToManager(manager, employee);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Manager setHrToManager(final Manager manager, final HrTeamMember hrTeamMember)
      throws SubjectDoesNotExistException {
    return managerDAO.setHrToManager(manager, hrTeamMember);
  }
}
