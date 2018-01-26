package org.openhr.controller.common;

import org.hibernate.HibernateException;
import org.openhr.exception.ApplicationDoesNotExistException;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.exception.UserAlreadyExists;
import org.openhr.exception.UserDoesNotExist;
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
  public ErrorInfo handleSubjectNotFound(final HttpServletRequest req, final Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ApplicationDoesNotExistException.class)
  public ErrorInfo handleApplicationNotFound(final HttpServletRequest req, final Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(HibernateException.class)
  public ErrorInfo handleHibernateException(final HttpServletRequest req, final Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  @ExceptionHandler(UserAlreadyExists.class)
  public ErrorInfo handleUserAlreadyExistsException(final HttpServletRequest req, final Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserDoesNotExist.class)
  public ErrorInfo handleUserDoesNotExistException(final HttpServletRequest req, final Exception ex) {
    return new ErrorInfo(req.getRequestURL().toString(), ex);
  }
}
