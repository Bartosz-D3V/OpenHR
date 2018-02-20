package org.openhr.application.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.manager.facade.ManagerFacade;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("Not found");
  private final static Manager manager = new Manager();
  private final static Employee employee = new Employee();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ManagerFacade managerFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser()
  public void addManagerShouldAcceptManagerObject() throws Exception {
    final String managerAsJson = objectMapper.writeValueAsString(manager);

    final MvcResult result = mockMvc
      .perform(post("/managers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(managerAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void updateManagerShouldAcceptManagerObject() throws Exception {
    final String managerAsJson = objectMapper.writeValueAsString(manager);

    final MvcResult result = mockMvc
      .perform(put("/managers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(managerAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void getEmployeesShouldHandleError() throws Exception {
    when(managerFacade.getEmployees(1)).thenThrow(mockException);

    final MvcResult result = mockMvc
      .perform(get("/managers/{subjectId}/employees", 1L))
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockException, result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void getEmployeesShouldReturnEmployees() throws Exception {
    final Set<Employee> employeeSet = new HashSet<>();
    employeeSet.add(new Employee());
    final String employeeSetAsJson = objectMapper.writeValueAsString(employeeSet);
    when(managerFacade.getEmployees(1)).thenReturn(employeeSet);

    final MvcResult result = mockMvc
      .perform(get("/managers/{subjectId}/employees", 1L))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(employeeSetAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser()
  public void addEmployeeToManagerShouldHandleError() throws Exception {
    employee.setManager(new Manager());
    final String employeeAsJson = objectMapper.writeValueAsString(employee);
    doThrow(mockException).when(managerFacade).addEmployeeToManager(anyObject(), anyLong());

    final MvcResult result = mockMvc
      .perform(post("/managers/{subjectId}/employee-assignment", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(employeeAsJson))
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockException, result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void addEmployeeToManagerShouldAddEmployeeToManager() throws Exception {
    employee.setManager(new Manager());
    final String employeeAsJson = objectMapper.writeValueAsString(employee);

    final MvcResult result = mockMvc
      .perform(post("/managers/{subjectId}/employee-assignment", 1L)
        .contentType(MediaType.APPLICATION_JSON)
        .content(employeeAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }
}
