package org.openhr.application.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.employee.facade.EmployeeFacade;
import org.openhr.common.domain.subject.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
  private final static Employee employee = new Employee();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private EmployeeFacade employeeFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void getEmployeesOfManagerShouldReturnEmployeesOfManager() throws Exception {
    final List<Employee> employees = new ArrayList<>();
    employees.add(employee);
    final String employeesAsJSON = objectMapper.writeValueAsString(employees);
    when(employeeFacade.getEmployeesOfManager(1L)).thenReturn(employees);

    final MvcResult result = mockMvc
      .perform(get("/employees")
        .param("managerId", String.valueOf(1L)))
      .andExpect(status().isOk())
      .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(employeesAsJSON, result.getResponse().getContentAsString());
  }
}
