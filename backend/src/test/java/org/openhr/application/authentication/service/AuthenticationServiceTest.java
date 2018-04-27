package org.openhr.application.authentication.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.application.user.service.UserService;
import org.openhr.common.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {

  @Autowired private UserService userService;

  @Test
  public void encodePasswordShouldCreateValidBCryptCiphertext() {
    final String mockPassword = "password";

    assertNotEquals(userService.encodePassword(mockPassword), mockPassword);
  }

  @Test
  public void setBasicUserRolesShouldReturnListWithMemberEnum() {
    final List<UserRole> userRoleList = userService.setBasicUserRoles(new User());

    assertTrue(userRoleList.get(0).getUserRole().equals(Role.EMPLOYEE));
  }
}
