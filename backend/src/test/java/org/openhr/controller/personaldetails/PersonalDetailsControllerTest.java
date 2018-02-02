package org.openhr.controller.personaldetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.common.ErrorInfo;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.facade.personaldetails.PersonalDetailsFacade;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonalDetailsController.class)
public class PersonalDetailsControllerTest {

  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static String MOCK_URL = "localhost:8080/api/personal-details";
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("DB Error");
  private final static ErrorInfo mockError = new ErrorInfo(MOCK_URL, mockException);
  private final static Address mockAddress = new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London",
    "UK");
  private final static PersonalInformation mockPersonalInformation = new PersonalInformation("John", null);
  private final static ContactInformation mockContactInformation = new ContactInformation("0123456789", "j.x@g.com",
    mockAddress);
  private final static EmployeeInformation mockEmployeeInformation = new EmployeeInformation("S8821 B", "Tester",
    "12A", null, null);
  private final static Subject mockSubject = new Subject("John", "Xavier", mockPersonalInformation,
    mockContactInformation, mockEmployeeInformation);

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonalDetailsFacade personalDetailsFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser()
  public void getSubjectDetailsShouldHandleError() throws Exception {
    when(personalDetailsFacade.getSubjectDetails(1)).thenThrow(mockException);

    final MvcResult result = mockMvc
      .perform(get("/personal-details")
        .param("subjectId", "1"))
      .andExpect(status().isNotFound())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockException, result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void getSubjectDetailsShouldReturnSubject() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockSubject);
    when(personalDetailsFacade.getSubjectDetails(1)).thenReturn(mockSubject);

    final MvcResult result = mockMvc
      .perform(get("/personal-details")
        .param("subjectId", "1"))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
    assertEquals(subjectAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser()
  public void createSubjectShouldHandleError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockSubject);
    doThrow(new HibernateException("DB Error")).when(personalDetailsFacade).addSubject(any());

    final MvcResult result = mockMvc
      .perform(post("/personal-details")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void createSubjectShouldCreateSubject() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockSubject);

    final MvcResult result = mockMvc
      .perform(post("/personal-details")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void updateSubjectShouldHandleError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockSubject);
    doThrow(new HibernateException("DB Error")).when(personalDetailsFacade).updateSubject(anyLong(), any());

    final MvcResult result = mockMvc
      .perform(put("/personal-details")
        .param("subjectId", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser()
  public void updateSubjectShouldUpdateSubject() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockSubject);

    final MvcResult result = mockMvc
      .perform(put("/personal-details")
        .param("subjectId", String.valueOf(mockSubject.getSubjectId()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(subjectAsJson))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void updateSubjectPersonalInformationShouldUpdatePersonalInformation() throws Exception {
    final String personalInformationAsJson = objectMapper.writeValueAsString(mockPersonalInformation);

    final MvcResult result = mockMvc
      .perform(put("/personal-details/personal-information")
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
    doThrow(new HibernateException("DB Error")).when(personalDetailsFacade)
      .updateSubjectPersonalInformation(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(put("/personal-details/personal-information")
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
      .perform(put("/personal-details/contact-information")
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
    doThrow(new HibernateException("DB Error")).when(personalDetailsFacade)
      .updateSubjectContactInformation(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(put("/personal-details/contact-information")
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
      .perform(put("/personal-details/employee-information")
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
    doThrow(new HibernateException("DB Error")).when(personalDetailsFacade)
      .updateSubjectEmployeeInformation(anyLong(), anyObject());

    final MvcResult result = mockMvc
      .perform(put("/personal-details/employee-information")
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
      .perform(delete("/personal-details")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", String.valueOf(mockSubject.getSubjectId())))
      .andExpect(status().isOk())
      .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser()
  public void deleteSubjectShouldHandleError() throws Exception {
    doThrow(new HibernateException("DB Error")).when(personalDetailsFacade).deleteSubject(anyLong());

    final MvcResult result = mockMvc
      .perform(delete("/personal-details")
        .contentType(MediaType.APPLICATION_JSON)
        .param("subjectId", "1"))
      .andExpect(status().isInternalServerError())
      .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }
}
