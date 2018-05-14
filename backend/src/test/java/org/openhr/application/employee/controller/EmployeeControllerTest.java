package org.openhr.application.employee.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.employee.facade.EmployeeFacade;
import org.openhr.application.manager.domain.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired private MockMvc mockMvc;

  @MockBean private EmployeeFacade employeeFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void getEmployeeShouldReturnSingleEmployee() throws Exception {
    when(employeeFacade.getEmployee(1L)).thenReturn(new Employee());
    final String employeesAsJSON = objectMapper.writeValueAsString(new Employee());

    final MvcResult result =
        mockMvc
            .perform(get("/employees/{subjectId}", 1L).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(employeesAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getEmployeesShouldReturnAllEmployees() throws Exception {
    final List<Employee> employees = new ArrayList<>();
    employees.add(new Employee());
    employees.add(new Employee());
    when(employeeFacade.getEmployees()).thenReturn(employees);
    final String employeesAsJSON = objectMapper.writeValueAsString(employees);

    final MvcResult result =
        mockMvc.perform(get("/employees")).andExpect(status().isOk()).andReturn();

    assertNull(result.getResolvedException());
    assertEquals(employeesAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void createEmployeeShouldReturnCreatedEmployee() throws Exception {
    when(employeeFacade.createEmployee(anyObject())).thenReturn(new Employee());
    final String employeesAsJSON = objectMapper.writeValueAsString(new Employee());

    final MvcResult result =
        mockMvc
            .perform(
                post("/employees").content(employeesAsJSON).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(employeesAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void updateEmployeeShouldReturnUpdatedEmployee() throws Exception {
    when(employeeFacade.updateEmployee(anyLong(), anyObject())).thenReturn(new Employee());
    final String employeesAsJSON = objectMapper.writeValueAsString(new Employee());

    final MvcResult result =
        mockMvc
            .perform(
                put("/employees/{subjectId}", 1L)
                    .content(employeesAsJSON)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(employeesAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void deleteEmployeeShouldAcceptSubjectIdAsPathVariable() throws Exception {
    final MvcResult result =
        mockMvc
            .perform(delete("/employees/{subjectId}", 1L))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void setManagerToEmployeeShouldAssignAndReturnManger() throws Exception {
    final Employee employee = new Employee();
    final Manager manager = new Manager();
    employee.setManager(manager);
    final String managerAsJSON = objectMapper.writeValueAsString(manager);
    when(employeeFacade.setManagerToEmployee(anyLong(), anyObject())).thenReturn(manager);

    final MvcResult result =
        mockMvc
            .perform(
                put("/employees/{employeeId}/manager-assignment", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(managerAsJSON))
            .andExpect(status().isAccepted())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(managerAsJSON, result.getResponse().getContentAsString());
  }
}
