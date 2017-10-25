package org.openhr.controller.personaldetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.common.ErrorInfo;
import org.openhr.dao.subject.SubjectDAO;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebMvcTest(PersonalDetailsController.class)
public class PersonalDetailsControllerTest {

  private final static String MOCK_URL = "localhost:8080/api/personal-details";
  private final static SubjectDoesNotExistException mockException = new SubjectDoesNotExistException("Not found");
  private final static ErrorInfo mockError = new ErrorInfo(MOCK_URL, mockException);
  private MockMvc mockMvc;

  @Mock
  private SubjectDAO subjectDAO;

  @Mock
  private HttpServletRequest httpServletRequest;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(PersonalDetailsController.class).build();
  }

  @Test
  @DisplayName("getSubjectDetails should return Error as a domain object with appropriate headers set")
  public void getSubjectDetailsShouldHandleError() throws Exception {
    when(this.subjectDAO.getSubjectDetails(1L)).thenThrow(mockException);

    MvcResult result = this.mockMvc
            .perform(get("/personal-details")
                    .param("subjectId", "1L"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andReturn();
  }

}