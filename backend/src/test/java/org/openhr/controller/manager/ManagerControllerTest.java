package org.openhr.controller.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.domain.subject.Employee;
import org.openhr.domain.subject.Manager;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.facade.manager.ManagerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("DB Error");

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ManagerFacade managerFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getManagerShouldAcceptManagerObject() throws Exception {
    final Manager manager = new Manager();
    final String managerAsJson = objectMapper.writeValueAsString(manager);

    final MvcResult result = mockMvc
      .perform(post("/manager")
        .contentType(MediaType.APPLICATION_JSON)
        .content(managerAsJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  public void getEmployeesShouldHandleError() throws Exception {
    when(managerFacade.getEmployees(1)).thenThrow(mockException);

    final MvcResult result = mockMvc
      .perform(get("/manager/employees")
        .param("managerId", "1"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockException, result.getResolvedException());
  }

  @Test
  public void getEmployeesShouldReturnEmployees() throws Exception {
    final Set<Employee> employeeSet = new HashSet<>();
    employeeSet.add(new Employee());
    final String employeeSetAsJson = objectMapper.writeValueAsString(employeeSet);
    when(managerFacade.getEmployees(1)).thenReturn(employeeSet);

    final MvcResult result = mockMvc
      .perform(get("/manager/employees")
        .param("managerId", "1"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(employeeSetAsJson, result.getResponse().getContentAsString());
  }
}
