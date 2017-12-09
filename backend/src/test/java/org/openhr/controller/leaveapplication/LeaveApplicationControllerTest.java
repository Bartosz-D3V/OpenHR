package org.openhr.controller.leaveapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.common.ErrorInfo;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.enumeration.Role;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.facade.leaveapplication.LeaveApplicationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LeaveApplicationFacade leaveApplicationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getLeaveApplicationShouldReturnAnApplication() throws Exception {
    final String leaveApplicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    when(leaveApplicationFacade.getLeaveApplication(anyLong())).thenReturn(mockLeaveApplication);

    MvcResult result = mockMvc
      .perform(get("/leave-application")
        .param("applicationId", "1"))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(leaveApplicationAsJson, result.getResponse().getContentAsString());
  }

  @Test
  public void getLeaveApplicationShouldHandle404Exception() throws Exception {
    when(leaveApplicationFacade.getLeaveApplication(anyLong())).thenThrow(mock404Exception);

    MvcResult result = mockMvc
      .perform(get("/leave-application")
        .param("applicationId", "1"))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void getLeaveApplicationShouldHandleHibernateError() throws Exception {
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .getLeaveApplication(anyLong());

    MvcResult result = mockMvc
      .perform(get("/leave-application")
        .param("applicationId", "1"))
      .andDo(print())
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void createLeaveApplicationShouldHandle404Error() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockSubject404Exception).when(leaveApplicationFacade)
      .createLeaveApplication(anyLong(), anyObject());

    MvcResult result = mockMvc
      .perform(post("/leave-application")
        .param("subjectId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(applicationAsJson))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockSubject404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void createLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .createLeaveApplication(anyLong(), anyObject());

    MvcResult result = mockMvc
      .perform(post("/leave-application")
        .param("subjectId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andDo(print())
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void createLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);

    MvcResult result = mockMvc
      .perform(post("/leave-application")
        .param("subjectId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(applicationAsJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  public void updateLeaveApplicationShouldHandle404Error() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mock404Exception).when(leaveApplicationFacade)
      .updateLeaveApplication(anyObject());

    MvcResult result = mockMvc
      .perform(put("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void updateLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .updateLeaveApplication(anyObject());

    MvcResult result = mockMvc
      .perform(put("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andDo(print())
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void updateLeaveApplicationShouldUpdateAnApplication() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);

    MvcResult result = mockMvc
      .perform(put("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(applicationAsJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  public void rejectLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    MvcResult result = mockMvc
      .perform(put("/leave-application/reject")
        .param("applicationId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  public void rejectLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .rejectLeaveApplication(anyObject(), anyLong());

    MvcResult result = mockMvc
      .perform(put("/leave-application/reject")
        .param("applicationId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andDo(print())
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void rejectLeaveApplicationShouldHandle404Error() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);
    doThrow(mock404Exception).when(leaveApplicationFacade)
      .rejectLeaveApplication(anyObject(), anyLong());

    MvcResult result = mockMvc
      .perform(put("/leave-application/reject")
        .param("applicationId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void approveLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    MvcResult result = mockMvc
      .perform(put("/leave-application/approve")
        .param("applicationId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  public void approveLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);
    doThrow(mockHibernateException).when(leaveApplicationFacade)
      .approveLeaveApplication(anyObject(), anyLong());

    MvcResult result = mockMvc
      .perform(put("/leave-application/approve")
        .param("applicationId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andDo(print())
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void approveLeaveApplicationShouldHandle404Error() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);
    doThrow(mock404Exception).when(leaveApplicationFacade)
      .approveLeaveApplication(anyObject(), anyLong());

    MvcResult result = mockMvc
      .perform(put("/leave-application/approve")
        .param("applicationId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(roleAsJson))
      .andDo(print())
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

}
