package org.openhr.application.user.controller;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.application.user.facade.UserFacade;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
  private final UserFacade userFacade;

  public UserController(final UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @RequestMapping(
    value = "/{userId}",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  public User getUser(@PathVariable final long userId) {
    return userFacade.getUser(userId);
  }

  @RequestMapping(
    method = RequestMethod.POST,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ResponseBody
  public void registerUser(@RequestBody final User user) throws UserAlreadyExists {
    userFacade.registerUser(user);
  }

  @RequestMapping(
    value = "/{userId}",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public User updateUser(@PathVariable final long userId, @RequestBody final User user) {
    return userFacade.updateUser(userId, user);
  }

  @RequestMapping(
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public User getUserBySubjectId(@RequestParam final long subjectId) {
    return userFacade.getUserBySubjectId(subjectId);
  }

  @RequestMapping(method = RequestMethod.HEAD)
  @ResponseBody
  public HttpEntity<String> getUserByUsername(@RequestParam final String username) {
    final HttpHeaders httpHeaders = new HttpHeaders();
    try {
      userFacade.getUserByUsername(username);
      httpHeaders.set("usernameTaken", Boolean.toString(true));
    } catch (final UserDoesNotExist userDoesNotExist) {
      httpHeaders.set("usernameTaken", Boolean.toString(false));
    }
    return ResponseEntity.noContent().headers(httpHeaders).build();
  }

  @RequestMapping(value = "/{userId}/notifications", method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateNotificationsSettings(
      @PathVariable final long userId, @RequestParam final boolean notificationsTurnedOn) {
    userFacade.updateNotificationsSettings(userId, notificationsTurnedOn);
  }

  @RequestMapping(
    value = "/auth/{userId}",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  public void updatePassword(
      @PathVariable final long userId, @RequestBody final PasswordDTO passwordDTO)
      throws UserDoesNotExist, ValidationException, SubjectDoesNotExistException {
    userFacade.updatePassword(userId, passwordDTO);
  }
}
