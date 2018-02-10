package org.openhr.leaveapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.leaveapplication.controller.LeaveApplicationController;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.enumeration.Role;
import org.openhr.application.leaveapplication.facade.LeaveApplicationFacade;
import org.openhr.common.domain.error.ErrorInfo;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LeaveApplicationController.class)
public class LeaveApplicationControllerTest {

  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static String MOCK_URL = "localhost:8080/api/leave-application";
  private final static ApplicationDoesNotExistException mock404Exception =
    new ApplicationDoesNotExistException("Not found");
  private final static SubjectDoesNotExistException mockSubject404Exception =
    new SubjectDoesNotExistException("Subject not found");
  private final static HibernateException mockHibernateException = new HibernateException("DB error");
  private final static ErrorInfo mock404Error = new ErrorInfo(MOCK_URL, mock404Exception);
  private final static ErrorInfo mockSubject404Error = new ErrorInfo(MOCK_URL, mockSubject404Exception);
  private final static ErrorInfo mockHibernateError = new ErrorInfo(MOCK_URL, mockHibernateException);
  private final static LeaveApplication mockLeaveApplication = new LeaveApplication(null, null);
  private final static LeaveType leaveType = new LeaveType("Annual Leave", "Just a annual leave you've waited for!");

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LeaveApplicationFacade leaveApplicationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockLeaveApplication.setLeaveType(leaveType);
  }

  @Test
  @WithMockUser()
  public void getLeaveApplicationShouldReturnAnApplication() throws Exception {
    final String leaveApplicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    when(leaveApplicationFacade.getLeaveApplication(anyLong())).thenReturn(mockLeaveApplication);

    final MvcResult result = mockMvc
      .perform(get("/leave-application")
        .param("applicationId", "1"))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(leaveApplicationAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser()
  public void getLeaveApplicationShouldHandle404Exception() throws Exception {
    when(leaveApplicationFacade.getLeaveApplication(anyLong())).thenThrow(mock404Exception);

    final MvcResult result = mockMvc
      .perform(get("/leave-application")
        .param("applicationId", "1"))
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void getLeaveApplicationShouldHandleHibernateError() throws Exception {
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .getLeaveApplication(anyLong());

    final MvcResult result = mockMvc
      .perform(get("/leave-application")
        .param("applicationId", "1"))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void createLeaveApplicationShouldHandle404Error() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockSubject404Exception).when(leaveApplicationFacade)
      .createLeaveApplication(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(post("/leave-application/{subjectId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(applicationAsJson))
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockSubject404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void createLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .createLeaveApplication(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(post("/leave-application/{subjectId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void createLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);

    final MvcResult result = mockMvc
      .perform(post("/leave-application/{subjectId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(applicationAsJson))
      .andExpect(status().isCreated())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void updateLeaveApplicationShouldHandle404Error() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mock404Exception).when(leaveApplicationFacade)
      .updateLeaveApplication(anyObject());

    final MvcResult result = mockMvc
      .perform(put("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void updateLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .updateLeaveApplication(anyObject());

    final MvcResult result = mockMvc
      .perform(put("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void updateLeaveApplicationShouldUpdateAnApplication() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);

    final MvcResult result = mockMvc
      .perform(put("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(applicationAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void rejectLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    final MvcResult result = mockMvc
      .perform(put("/leave-application/reject")
        .param("processInstanceId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void rejectLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .rejectLeaveApplication(anyObject(), anyString());

    final MvcResult result = mockMvc
      .perform(put("/leave-application/reject")
        .param("processInstanceId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void approveLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    final MvcResult result = mockMvc
      .perform(put("/leave-application/approve")
        .param("processInstanceId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void approveLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .approveLeaveApplication(anyObject(), anyString());

    final MvcResult result = mockMvc
      .perform(put("/leave-application/approve")
        .param("processInstanceId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

}
