package org.openhr.application.manager.dao;

import java.util.Locale;
import java.util.Set;
import org.hibernate.SessionFactory;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.util.bean.BeanUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ManagerDAOImpl extends BaseDAO implements ManagerDAO {
  private final MessageSource messageSource;

  public ManagerDAOImpl(final SessionFactory sessionFactory, final MessageSource messageSource) {
    super(sessionFactory);
    this.messageSource = messageSource;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Set<Employee> getManagersEmployees(final long subjectId)
      throws SubjectDoesNotExistException {
    return getManager(subjectId).getEmployees();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Manager addManager(final Manager manager) {
    super.save(manager);
    return manager;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Manager updateManager(final long subjectId, final Manager manager)
      throws SubjectDoesNotExistException {
    final Manager savedManager = getManager(subjectId);
    BeanUtil.copyNotNullProperties(
        manager.getPersonalInformation(),
        savedManager.getPersonalInformation(),
        "personalInformationId");
    BeanUtil.copyNotNullProperties(
        manager.getContactInformation(),
        savedManager.getContactInformation(),
        "contactInformationId");
    BeanUtil.copyNotNullProperties(
        manager.getContactInformation().getAddress(),
        savedManager.getContactInformation().getAddress());
    BeanUtil.copyNotNullProperties(
        manager.getEmployeeInformation(),
        savedManager.getEmployeeInformation(),
        "employeeInformationId");
    BeanUtil.copyNotNullProperties(
        manager.getHrInformation(), savedManager.getHrInformation(), "hrInformationId");
    BeanUtil.copyNotNullProperties(manager.getRole(), savedManager.getRole());
    BeanUtil.copyNotNullProperties(
        manager.getUser(), savedManager.getUser(), "userId", "userRoles");
    BeanUtil.copyNotNullProperties(manager.getEmployees(), savedManager.getEmployees());
    BeanUtil.copyNotNullProperties(manager.getHrTeamMember(), savedManager.getHrTeamMember());
    super.merge(savedManager);

    return manager;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteManager(final Manager manager) {
    super.delete(manager);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Manager getManager(final long subjectId) throws SubjectDoesNotExistException {
    final Manager manager = (Manager) super.get(Manager.class, subjectId);
    if (manager == null) {
      throw new SubjectDoesNotExistException(
          messageSource.getMessage("error.subjectdoesnotexist", null, Locale.getDefault()));
    }
    return manager;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void addEmployeeToManager(final Manager manager, final Employee employee) {
    final Set<Employee> employees = manager.getEmployees();
    employees.add(employee);
    manager.setEmployees(employees);
    employee.setManager(manager);
    super.merge(employee);
    super.merge(manager);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public Manager setHrToManager(final Manager manager, final HrTeamMember hrTeamMember) {
    final Set<Manager> managers = hrTeamMember.getManagers();
    manager.setHrTeamMember(hrTeamMember);
    managers.add(manager);
    hrTeamMember.setManagers(managers);
    super.merge(manager);
    super.merge(hrTeamMember);

    return manager;
  }
}
