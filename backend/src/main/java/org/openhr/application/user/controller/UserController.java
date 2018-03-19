package org.openhr.application.user.controller;

import org.openhr.application.user.domain.User;
import org.openhr.application.user.dto.PasswordDTO;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.application.user.facade.UserFacade;
import org.openhr.common.exception.UserDoesNotExist;
import org.openhr.common.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.ACCEPTED)
  @ResponseBody
  public void registerUser(@RequestBody final User user) throws UserAlreadyExists {
    userFacade.registerUser(user);
  }

  @RequestMapping(value = "/auth/{subjectId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ResponseBody
  public void updatePassword(@PathVariable final long subjectId, @RequestBody PasswordDTO passwordDTO)
    throws UserDoesNotExist, ValidationException {
    userFacade.updatePassword(subjectId, passwordDTO);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public User getUserByUsername(@PathVariable("username") final String username) throws UserDoesNotExist {
    return userFacade.findByUsername(username);
  }
}
