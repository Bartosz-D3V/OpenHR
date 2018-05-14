package org.openhr.application.employee.service;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.repository.EmployeeRepository;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.exception.UserAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EmployeeServiceTest {

  @Autowired EmployeeService employeeService;

  @SpyBean UserService userService;

  @MockBean EmployeeRepository employeeRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = UserAlreadyExists.class)
  public void createEmployeeShouldThrowAnErrorIfUsernameIsTaken() throws UserAlreadyExists {
    when(userService.isUsernameFree("testUsername")).thenReturn(false);

    final Employee employee = new Employee();
    employee.setUser(new User("testUsername", null));

    employeeService.createEmployee(employee);
  }
}
