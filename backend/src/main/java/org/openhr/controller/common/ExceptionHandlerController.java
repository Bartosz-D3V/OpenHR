package org.openhr.controller.common;

import org.hibernate.HibernateException;
import org.openhr.controller.personaldetails.SubjectDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(SubjectDoesNotExistException.class)
  public ErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(HibernateException.class)
  public ErrorInfo handleHibernateException(HttpServletRequest req, Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

}
