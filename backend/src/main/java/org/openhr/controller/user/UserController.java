package org.openhr.controller.user;

import org.openhr.domain.user.User;
import org.openhr.facade.user.UserFacade;
import org.springframework.http.MediaType;
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
}
