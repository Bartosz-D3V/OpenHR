package org.openhr.controller.personaldetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.common.ErrorInfo;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.Subject;
import org.openhr.facade.personaldetails.PersonalDetailsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonalDetailsController.class)
public class PersonalDetailsControllerTest {

  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static String MOCK_URL = "localhost:8080/api/personal-details";
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("DB Error");
  private final static ErrorInfo mockError = new ErrorInfo(MOCK_URL, mockException);
  private final static Subject subject = new Subject(1L, "John", null, "White",
          null, "Manager", "123456789", "john.white@cor.com", new Address());

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonalDetailsFacade personalDetailsFacade;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void getSubjectDetailsShouldHandleError() throws Exception {
    when(this.personalDetailsFacade.getSubjectDetails(1)).thenThrow(mockException);

    this.mockMvc
            .perform(get("/personal-details")
                    .param("subjectId", "1"))
            .andDo(print())
            .andExpect(status().isNotFound());
  }

  @Test
  public void getSubjectDetailsShouldReturnSubject() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(subject);
    when(this.personalDetailsFacade.getSubjectDetails(1)).thenReturn(subject);

    MvcResult result = this.mockMvc
            .perform(get("/personal-details")
                    .param("subjectId", "1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    assertEquals(subjectAsJson, result.getResponse().getContentAsString());
  }

  @Test
  public void createSubjectShouldHandleError() throws Exception {
    final String subjectAsJson = objectMapper.writeValueAsString(subject);
    doThrow(new HibernateException("DB Error")).when(this.personalDetailsFacade).addSubject(any());

    MvcResult result = this.mockMvc
            .perform(post("/personal-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(subjectAsJson))
            .andDo(print())
            .andExpect(status().isInternalServerError())
            .andReturn();
    assertEquals(mockError.getMessage(), result.getResolvedException().getMessage());
  }
}
