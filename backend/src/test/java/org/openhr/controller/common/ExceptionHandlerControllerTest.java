package org.openhr.controller.common;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionHandlerControllerTest {

  private final static String MOCK_URL = "localhost:8080/api/personal-details";
  private final ExceptionHandlerController exceptionHandlerController = new ExceptionHandlerController();

  @Mock
  private HttpServletRequest httpServletRequest;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(this.httpServletRequest.getRequestURL()).thenReturn(new StringBuffer(MOCK_URL));
  }

  @Test
  public void handleBadRequestShouldConvertErrorIntoDomainObjectWithAppropriateHeaders() {
    final SubjectDoesNotExistException mockError = new SubjectDoesNotExistException("Subject not found");
    final ErrorInfo returnedInfo = this.exceptionHandlerController.handleBadRequest(this.httpServletRequest, mockError);
    final ErrorInfo expectedInfo = new ErrorInfo(MOCK_URL, mockError);

    assertEquals(expectedInfo.getUrl(), returnedInfo.getUrl());
    assertEquals(expectedInfo.getMessage(), returnedInfo.getMessage());
  }

  @Test
  public void handleHibernateExceptionShouldConvertErrorIntoDomainObjectWithAppropriateHeaders() {
    final HibernateException mockError = new HibernateException("DB error");
    final ErrorInfo returnedInfo = this.exceptionHandlerController.handleHibernateException(this.httpServletRequest,
            mockError);
    final ErrorInfo expectedInfo = new ErrorInfo(MOCK_URL, mockError);

    assertEquals(expectedInfo.getUrl(), returnedInfo.getUrl());
    assertEquals(expectedInfo.getMessage(), returnedInfo.getMessage());
  }

}