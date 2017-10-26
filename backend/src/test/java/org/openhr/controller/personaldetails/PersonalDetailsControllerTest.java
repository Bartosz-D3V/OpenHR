package org.openhr.controller.personaldetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.common.ErrorInfo;
import org.openhr.dao.subject.SubjectDAO;
import org.openhr.domain.address.Address;
import org.openhr.domain.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDate;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(PersonalDetailsController.class)
public class PersonalDetailsControllerTest {

  private final static ObjectMapper objectMapper = new ObjectMapper();
  private final static String MOCK_URL = "localhost:8080/api/personal-details";
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("Not found");
  private final static ErrorInfo mockError = new ErrorInfo(MOCK_URL, mockException);
  private final Subject subject = new Subject(1L, "John", null, "White",
          LocalDate.now(), "Manager", "123456789", "john.white", new Address());
  private MockMvc mockMvc;

  @Mock
  private SubjectDAO subjectDAO;

  @Mock
  private HttpServletRequest httpServletRequest;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(PersonalDetailsController.class).build();
  }

  @Test
  public void getSubjectDetailsShouldHandleError() throws Exception {
    when(this.subjectDAO.getSubjectDetails(1L)).thenThrow(mockException);

    MvcResult result = this.mockMvc
            .perform(get("/personal-details")
                    .param("subjectId", "1L"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andReturn();
  }

  @Test
  public void createSubjectShouldHandleError() throws Exception {
    doThrow(new HibernateException("DB Error")).when(this.subjectDAO).addSubject(any());

    final String subjectAsJson = objectMapper.writeValueAsString(this.subject);

    MvcResult result = this.mockMvc
            .perform(post("/personal-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(subjectAsJson))
            .andDo(print())
            .andExpect(status().isInternalServerError())
            .andReturn();
  }

}