package org.openhr.controller.leaveapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.common.ErrorInfo;
import org.openhr.domain.application.LeaveApplication;
import org.openhr.domain.subject.Subject;
import org.openhr.enumeration.Role;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LeaveApplicationController.class)
public class LeaveApplicationControllerTest {

  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static String MOCK_URL = "localhost:8080/api/leave-application";
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("DB Error");
  private final static ErrorInfo mockError = new ErrorInfo(MOCK_URL, mockException);
  private final static Subject mockSubject = new Subject("John", "Xavier", Role.EMPLOYEE);
  private final static LeaveApplication mockLeaveApplication = new LeaveApplication(mockSubject, null, null);

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LeaveApplicationFacade leaveApplicationFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createSubjectShouldHandleError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockLeaveApplication);
    doThrow(new HibernateException("DB Error")).when(leaveApplicationFacade).createLeaveApplication(any());

    MvcResult result = mockMvc
      .perform(post("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andDo(print())
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  public void createLeaveApplicationShouldCreateAnApplication() throws Exception {
    final String applicationAsJson = objectMapper.writeValueAsString(mockLeaveApplication);

    MvcResult result = mockMvc
      .perform(post("/leave-application")
        .contentType(MediaType.APPLICATION_JSON)
        .content(applicationAsJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

}
