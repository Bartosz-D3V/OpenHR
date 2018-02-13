package org.openhr.application.authentication.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.common.enumeration.UserPermissionRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTest {

  @Autowired
  private AuthenticationService authenticationService;

  @Test
  public void encodePasswordShouldCreateValidBCryptCiphertext() {
    final String mockPassword = "password";

    assertNotEquals(authenticationService.encodePassword(mockPassword), mockPassword);
  }

  @Test
  public void setBasicUserRolesShouldReturnListWithMemberEnum() {
    final List<UserRole> userRoleList = authenticationService.setBasicUserRoles(new User());

    assertTrue(userRoleList.get(0).getUserPermissionRole().equals(UserPermissionRole.MEMBER));
  }
}