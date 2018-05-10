package org.openhr.application.employee.dao;

import org.hibernate.SessionFactory;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.util.bean.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmployeeDAOImpl extends BaseDAO implements EmployeeDAO {

  public EmployeeDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Employee getEmployee(final long subjectId) {
    return (Employee) super.get(Employee.class, subjectId);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Employee createEmployee(final Employee employee) {
    super.save(employee);
    return employee;
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Employee updateEmployee(final long subjectId, final Employee employee) {
    final Employee savedEmployee = getEmployee(subjectId);
    BeanUtil.copyNotNullProperties(
        employee.getPersonalInformation(),
        savedEmployee.getPersonalInformation(),
        "personalInformationId");
    BeanUtil.copyNotNullProperties(
        employee.getContactInformation(),
        savedEmployee.getContactInformation(),
        "contactInformationId");
    BeanUtil.copyNotNullProperties(
        employee.getContactInformation().getAddress(),
        employee.getContactInformation().getAddress());
    BeanUtil.copyNotNullProperties(
        employee.getEmployeeInformation(),
        savedEmployee.getEmployeeInformation(),
        "employeeInformationId");
    BeanUtils.copyProperties(
        employee.getHrInformation(), savedEmployee.getHrInformation(), "hrInformationId");
    BeanUtil.copyNotNullProperties(employee.getRole(), savedEmployee.getRole());
    BeanUtil.copyNotNullProperties(
        employee.getUser(), savedEmployee.getUser(), "userId", "userRoles");
    super.merge(savedEmployee);
    return savedEmployee;
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void deleteEmployee(final Employee employee) {
    super.delete(employee);
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public Manager setManagerToEmployee(final long employeeId, final Manager manager) {
    final Manager fetchedManager = (Manager) super.get(Manager.class, manager.getSubjectId());
    final Employee savedEmployee = (Employee) super.get(Employee.class, employeeId);
    savedEmployee.setManager(fetchedManager);
    super.merge(savedEmployee);
    return fetchedManager;
  }
}
