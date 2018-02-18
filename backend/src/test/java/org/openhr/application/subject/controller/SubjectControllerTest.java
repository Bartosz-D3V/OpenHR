package org.openhr.application.subject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.subject.facade.SubjectFacade;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.error.ErrorInfo;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.Employee;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.HrInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {
  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static String MOCK_URL = "localhost:8080/api/subjects";
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("DB Error");
  private final static ErrorInfo mockError = new ErrorInfo(MOCK_URL, mockException);
  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London",
    "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com",
    mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester",
    "Core", "12A", null, null);
  private final static HrInformation mockHrInformation = new HrInformation(25L);
  private final static Subject mockSubject = new Employee("John", "Xavier", mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation, mockHrInformation, new User());

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SubjectFacade subjectFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser()
  public void getSubjectDetailsShouldHandleError() throws Exception {
    when(subjectFacade.getSubjectDetails(1)).thenThrow(mockException);

    final MvcResult result = mockMvc
      .perform(get("/subjects/{subjectId}", 1L))
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockException, result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void getSubjectDetailsShouldReturnSubject() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockSubject);
    when(subjectFacade.getSubjectDetails(1)).thenReturn(mockSubject);

    final MvcResult result = mockMvc
      .perform(get("/subjects/{subjectId}", 1L))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(subjectAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser()
  public void updateSubjectPersonalInformationShouldUpdatePersonalInformation() throws Exception {
    final String personalInformationAsJson = objectMapper.writeValueAsString(mockPersonalInformation);

    final MvcResult result = mockMvc
      .perform(put("/subjects/personal-information")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", String.valueOf(mockSubject.getSubjectId()))
        .content(personalInformationAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void updateSubjectPersonalInformationShouldHandleError() throws Exception {
    final String personalInformationAsJson = objectMapper.writeValueAsString(mockPersonalInformation);
    doThrow(new HibernateException("DB Error")).when(subjectFacade)
      .updateSubjectPersonalInformation(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(put("/subjects/personal-information")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", "1")
        .content(personalInformationAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void updateSubjectContactInformationShouldUpdateContactInformation() throws Exception {
    final String contactInformationAsJson = objectMapper.writeValueAsString(mockContactInformation);

    final MvcResult result = mockMvc
      .perform(put("/subjects/contact-information")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", String.valueOf(mockSubject.getSubjectId()))
        .content(contactInformationAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void updateSubjectContactInformationShouldHandleError() throws Exception {
    final String contactInformationAsJson = objectMapper.writeValueAsString(mockContactInformation);
    doThrow(new HibernateException("DB Error")).when(subjectFacade)
      .updateSubjectContactInformation(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(put("/subjects/contact-information")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", "1")
        .content(contactInformationAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void updateSubjectEmployeeInformationShouldUpdateContactInformation() throws Exception {
    final String employeeInformationAsJson = objectMapper.writeValueAsString(mockEmployeeInformation);

    final MvcResult result = mockMvc
      .perform(put("/subjects/employee-information")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", String.valueOf(mockSubject.getSubjectId()))
        .content(employeeInformationAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void updateSubjectEmployeeInformationShouldHandleError() throws Exception {
    final String employeeInformationAsJson = objectMapper.writeValueAsString(mockEmployeeInformation);
    doThrow(new HibernateException("DB Error")).when(subjectFacade)
      .updateSubjectEmployeeInformation(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(put("/subjects/employee-information")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", "1")
        .content(employeeInformationAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void deleteSubjectShouldDeleteSubject() throws Exception {
    final MvcResult result = mockMvc
      .perform(delete("/subjects")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", String.valueOf(mockSubject.getSubjectId())))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void deleteSubjectShouldHandleError() throws Exception {
    doThrow(new HibernateException("DB Error")).when(subjectFacade).deleteSubject(anyLong());

    final MvcResult result = mockMvc
      .perform(delete("/subjects")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", "1"))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }
}
