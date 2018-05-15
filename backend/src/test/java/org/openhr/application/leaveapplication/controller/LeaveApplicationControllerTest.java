package org.openhr.application.leaveapplication.controller;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.leaveapplication.domain.LeaveApplication;
import org.openhr.application.leaveapplication.domain.LeaveType;
import org.openhr.application.leaveapplication.facade.LeaveApplicationFacade;
import org.openhr.common.domain.error.ErrorInfo;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.ApplicationDoesNotExistException;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(LeaveApplicationController.class)
public class LeaveApplicationControllerTest {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final String MOCK_URL = "localhost:8080/api/leave-application";
  private static final ApplicationDoesNotExistException mock404Exception =
      new ApplicationDoesNotExistException("Not found");
  private static final SubjectDoesNotExistException mockSubject404Exception =
      new SubjectDoesNotExistException("Subject not found");
  private static final ValidationException mockValidationException =
      new ValidationException("Validation exception");
  private static final HibernateException mockHibernateException =
      new HibernateException("DB error");
  private static final ErrorInfo mock404Error = new ErrorInfo(MOCK_URL, mock404Exception);
  private static final ErrorInfo mockSubject404Error =
      new ErrorInfo(MOCK_URL, mockSubject404Exception);
  private static final ErrorInfo mockHibernateError =
      new ErrorInfo(MOCK_URL, mockHibernateException);
  private static final LeaveApplication mockLeaveApplication = new LeaveApplication(null, null);
  private static final LeaveType leaveType =
      new LeaveType("Annual Leave", "Just a annual leave you've waited for!");

  @Autowired private MockMvc mockMvc;

  @MockBean private LeaveApplicationFacade leaveApplicationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockLeaveApplication.setLeaveType(leaveType);
  }

  @Test
  @WithMockUser
  public void getLeaveApplicationShouldReturnAnApplication() throws Exception {
    final String leaveApplicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    when(leaveApplicationFacade.getLeaveApplication(1L)).thenReturn(mockLeaveApplication);

    final MvcResult result =
        mockMvc
            .perform(get("/leave-applications/{leaveApplicationId}", 1L))
            .andExpect(status().isOk())
            .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(leaveApplicationAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getLeaveApplicationShouldHandle404Exception() throws Exception {
    when(leaveApplicationFacade.getLeaveApplication(1L)).thenThrow(mock404Exception);

    final MvcResult result =
        mockMvc
            .perform(get("/leave-applications/{leaveApplicationId}", 1L))
            .andExpect(status().isNotFound())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void getLeaveApplicationShouldHandleHibernateError() throws Exception {
    doThrow(mockHibernateException).when(leaveApplicationFacade).getLeaveApplication(1L);

    final MvcResult result =
        mockMvc
            .perform(get("/leave-applications/{leaveApplicationId}", 1L))
            .andExpect(status().isInternalServerError())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void createLeaveApplicationShouldHandle404Error() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockSubject404Exception)
        .when(leaveApplicationFacade)
        .createLeaveApplication(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                post("/leave-applications")
                    .param("subjectId", String.valueOf(1L))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(applicationAsJson))
            .andExpect(status().isNotFound())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockSubject404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void createLeaveApplicationShouldHandleValidationError() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockValidationException)
        .when(leaveApplicationFacade)
        .createLeaveApplication(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                post("/leave-applications")
                    .param("subjectId", String.valueOf(1L))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(applicationAsJson))
            .andExpect(status().isBadRequest())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockValidationException.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void createLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockHibernateException)
        .when(leaveApplicationFacade)
        .createLeaveApplication(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                post("/leave-applications")
                    .param("subjectId", String.valueOf(1L))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(subjectAsJson))
            .andExpect(status().isInternalServerError())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void createLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);

    final MvcResult result =
        mockMvc
            .perform(
                post("/leave-applications")
                    .param("subjectId", String.valueOf(1L))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(applicationAsJson))
            .andExpect(status().isCreated())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void updateLeaveApplicationShouldHandle404Error() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mock404Exception)
        .when(leaveApplicationFacade)
        .updateLeaveApplication(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                put("/leave-applications/{leaveApplicationId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(subjectAsJson))
            .andExpect(status().isNotFound())
            .andReturn();

    assertNotNull(result.getResolvedException());
    assertEquals(mock404Error.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void getSubjectsLeaveApplicationsShouldAcceptSubjectIdAsParameter() throws Exception {
    final List<LeaveApplication> leaveApplicationList = new ArrayList<>();
    leaveApplicationList.add(mockLeaveApplication);
    final String leaveApplicationsAsJSON = objectMapper.writeValueAsString(leaveApplicationList);

    when(leaveApplicationFacade.getSubjectsLeaveApplications(1L)).thenReturn(leaveApplicationList);

    final MvcResult result =
        mockMvc
            .perform(get("/leave-applications").param("subjectId", String.valueOf(1L)))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(leaveApplicationsAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void updateLeaveApplicationShouldHandleHibernateError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(mockHibernateException)
        .when(leaveApplicationFacade)
        .updateLeaveApplication(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                put("/leave-applications/{leaveApplicationId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(subjectAsJson))
            .andExpect(status().isInternalServerError())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockHibernateError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void updateLeaveApplicationShouldUpdateAnApplication() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);

    final MvcResult result =
        mockMvc
            .perform(
                put("/leave-applications/{leaveApplicationId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(applicationAsJson))
            .andExpect(status().isOk())
            .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void rejectLeaveApplicationByManagerShouldRejectAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    final MvcResult result =
        mockMvc
            .perform(
                put("/leave-applications/manager-reject")
                    .param("processInstanceId", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(roleAsJson))
            .andExpect(status().isNoContent())
            .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void approveLeaveApplicationByManagerShouldApproveAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    final MvcResult result =
        mockMvc
            .perform(
                put("/leave-applications/manager-approve")
                    .param("processInstanceId", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(roleAsJson))
            .andExpect(status().isNoContent())
            .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void rejectLeaveApplicationByHRShouldRejectAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    final MvcResult result =
        mockMvc
            .perform(
                put("/leave-applications/hr-reject")
                    .param("processInstanceId", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(roleAsJson))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void approveLeaveApplicationByHRShouldApproveAnApplication() throws Exception {
    final String roleAsJson = objectMapper.writeValueAsString(Role.MANAGER);

    final MvcResult result =
        mockMvc
            .perform(
                put("/leave-applications/hr-approve")
                    .param("processInstanceId", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(roleAsJson))
            .andExpect(status().isNoContent())
            .andReturn();

    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void getAwaitingForActionLeaveApplicationsShouldAcceptSubjectIdAsParam() throws Exception {
    final List<LeaveApplication> leaveApplicationList = new ArrayList<>();
    leaveApplicationList.add(mockLeaveApplication);
    final String leaveApplicationsAsJSON = objectMapper.writeValueAsString(leaveApplicationList);

    when(leaveApplicationFacade.getAwaitingForActionLeaveApplications(1L))
        .thenReturn(leaveApplicationList);

    final MvcResult result =
        mockMvc
            .perform(get("/leave-applications/awaiting").param("subjectId", String.valueOf(1L)))
            .andExpect(status().isOk())
            .andReturn();

    assertNull(result.getResolvedException());
    assertEquals(leaveApplicationsAsJSON, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getLeaveTypesShouldReturnLeaveTypes() throws Exception {
    final List<LeaveType> leaveTypes = new ArrayList<>();
    leaveTypes.add(leaveType);
    final String leaveTypesAsJSON = objectMapper.writeValueAsString(leaveTypes);

    when(leaveApplicationFacade.getLeaveTypes()).thenReturn(leaveTypes);

    final MvcResult result =
        mockMvc.perform(get("/leave-applications/types")).andExpect(status().isOk()).andReturn();

    assertNull(result.getResolvedException());
    assertEquals(leaveTypesAsJSON, result.getResponse().getContentAsString());
  }
}
