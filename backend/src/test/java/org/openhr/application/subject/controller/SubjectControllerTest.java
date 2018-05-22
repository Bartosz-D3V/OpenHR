package org.openhr.application.subject.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.openhr.application.employee.domain.Employee;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.application.subject.facade.SubjectFacade;
import org.openhr.application.user.domain.User;
import org.openhr.common.domain.address.Address;
import org.openhr.common.domain.error.ErrorInfo;
import org.openhr.common.domain.subject.ContactInformation;
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

@RunWith(SpringRunner.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final String MOCK_URL = "localhost:8080/api/subjects";
  private static final SubjectDoesNotExistException mockException =
      new SubjectDoesNotExistException("DB Error");
  private static final ErrorInfo mockError = new ErrorInfo(MOCK_URL, mockException);
  private static final Address mockAddress =
      new Address("100 Fishbury Hs", "1 Ldn Road", null, "12 DSL", "London", "UK");
  private static final PersonalInformation mockPersonalInformation = new PersonalInformation();
  private static final ContactInformation mockContactInformation =
      new ContactInformation("0123456789", "j.x@g.com", mockAddress);
  private static final EmployeeInformation mockEmployeeInformation =
      new EmployeeInformation("S8821 B", "Tester", "Core", "12A", null, null);
  private static final HrInformation mockHrInformation = new HrInformation(25);
  private static final Subject mockSubject =
      new Employee(
          mockPersonalInformation,
          mockContactInformation,
          mockEmployeeInformation,
          mockHrInformation,
          new User());

  @Autowired private MockMvc mockMvc;

  @MockBean private SubjectFacade subjectFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @WithMockUser
  public void getSubjectsShouldReturnSubjects() throws Exception {
    final List<Subject> subjectList = new ArrayList<>();
    subjectList.add(mockSubject);
    final String subjectsAsJson = objectMapper.writeValueAsString(subjectList);
    when(subjectFacade.getSubjects()).thenReturn(subjectList);

    final MvcResult result =
        mockMvc.perform(get("/subjects")).andExpect(status().isOk()).andReturn();
    assertNull(result.getResolvedException());
    assertEquals(subjectsAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void getSubjectDetailsShouldHandleError() throws Exception {
    when(subjectFacade.getSubjectDetails(1)).thenThrow(mockException);

    final MvcResult result =
        mockMvc
            .perform(get("/subjects/{subjectId}", 1L))
            .andExpect(status().isNotFound())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockException, result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void getSubjectDetailsShouldReturnSubject() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(mockSubject);
    when(subjectFacade.getSubjectDetails(1)).thenReturn(mockSubject);

    final MvcResult result =
        mockMvc.perform(get("/subjects/{subjectId}", 1L)).andExpect(status().isOk()).andReturn();
    assertNull(result.getResolvedException());
    assertEquals(subjectAsJson, result.getResponse().getContentAsString());
  }

  @Test
  @WithMockUser
  public void updateSubjectPersonalInformationShouldUpdatePersonalInformation() throws Exception {
    final String personalInformationAsJson =
        objectMapper.writeValueAsString(mockPersonalInformation);

    final MvcResult result =
        mockMvc
            .perform(
                put("/subjects/personal-information")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("subjectId", String.valueOf(mockSubject.getSubjectId()))
                    .content(personalInformationAsJson))
            .andExpect(status().isNoContent())
            .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void updateSubjectPersonalInformationShouldHandleError() throws Exception {
    final String personalInformationAsJson =
        objectMapper.writeValueAsString(mockPersonalInformation);
    doThrow(new HibernateException("DB Error"))
        .when(subjectFacade)
        .updateSubjectPersonalInformation(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                put("/subjects/personal-information")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("subjectId", "1")
                    .content(personalInformationAsJson))
            .andExpect(status().isInternalServerError())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void updateSubjectContactInformationShouldUpdateContactInformation() throws Exception {
    final String contactInformationAsJson = objectMapper.writeValueAsString(mockContactInformation);

    final MvcResult result =
        mockMvc
            .perform(
                put("/subjects/contact-information")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("subjectId", String.valueOf(mockSubject.getSubjectId()))
                    .content(contactInformationAsJson))
            .andExpect(status().isNoContent())
            .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void updateSubjectContactInformationShouldHandleError() throws Exception {
    final String contactInformationAsJson = objectMapper.writeValueAsString(mockContactInformation);
    doThrow(new HibernateException("DB Error"))
        .when(subjectFacade)
        .updateSubjectContactInformation(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                put("/subjects/contact-information")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("subjectId", "1")
                    .content(contactInformationAsJson))
            .andExpect(status().isInternalServerError())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void updateSubjectEmployeeInformationShouldUpdateContactInformation() throws Exception {
    final String employeeInformationAsJson =
        objectMapper.writeValueAsString(mockEmployeeInformation);

    final MvcResult result =
        mockMvc
            .perform(
                put("/subjects/employee-information")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("subjectId", String.valueOf(mockSubject.getSubjectId()))
                    .content(employeeInformationAsJson))
            .andExpect(status().isNoContent())
            .andReturn();
    assertNull(result.getResolvedException());
  }

  @Test
  @WithMockUser
  public void updateSubjectEmployeeInformationShouldHandleError() throws Exception {
    final String employeeInformationAsJson =
        objectMapper.writeValueAsString(mockEmployeeInformation);
    doThrow(new HibernateException("DB Error"))
        .when(subjectFacade)
        .updateSubjectEmployeeInformation(anyLong(), anyObject());

    final MvcResult result =
        mockMvc
            .perform(
                put("/subjects/employee-information")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("subjectId", "1")
                    .content(employeeInformationAsJson))
            .andExpect(status().isInternalServerError())
            .andReturn();
    assertNotNull(result.getResolvedException());
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }

  @Test
  @WithMockUser
  public void getLightweightSubjectsShouldReturnLightweightSubject() throws Exception {
    final LightweightSubjectDTO subjectDTO = new LightweightSubjectDTO();
    subjectDTO.setFirstName("Test");
    subjectDTO.setLastName("Subject");
    subjectDTO.setPosition("Tester");
    subjectDTO.setSubjectId(1L);

    when(subjectFacade.getLightweightSubject(1L)).thenReturn(subjectDTO);

    final String subjectDTOAsJSON = objectMapper.writeValueAsString(subjectDTO);

    final MvcResult result =
        mockMvc
            .perform(get("/subjects/lightweight/{subjectId}", 1L))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals(subjectDTOAsJSON, result.getResponse().getContentAsString());
  }
}
