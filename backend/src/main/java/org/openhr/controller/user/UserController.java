package org.openhr.controller.user;

import org.openhr.domain.user.User;
import org.openhr.facade.user.UserFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
  private final UserFacade userFacade;

  public UserController(final UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void registerUser(@RequestBody final User user) {
    userFacade.registerUser(user);
  }

  @RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public User getUserByUsername(@PathVariable("username") final String username) {
    return userFacade.findByUsername(username);
  }
}