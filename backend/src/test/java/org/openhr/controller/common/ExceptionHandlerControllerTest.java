package org.openhr.controller.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionHandlerControllerTest {

  private final static String MOCK_URL = "localhost:8080/api/personal-details";
  private final ExceptionHandlerController exceptionHandlerController = new ExceptionHandlerController();

  @Mock
  private HttpServletRequest httpServletRequest;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(this.httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(MOCK_URL));
  }

  @Test
  @DisplayName("handleBadRequest should convert error into domain object")
  public void handleBadRequestShouldConvertErrorIntoDomainObjectWithAppropriateHeaders() {
    final SubjectDoesNotExistException mockError = new SubjectDoesNotExistException("Subject not found");
    final ErrorInfo returnedInfo = this.exceptionHandlerController.handleBadRequest(this.httpServletRequest, mockError);
    final ErrorInfo expectedInfo = new ErrorInfo(MOCK_URL, mockError);

    assertEquals(expectedInfo.getUrl(), returnedInfo.getUrl());
    assertEquals(expectedInfo.getMessage(), returnedInfo.getMessage());
  }

}