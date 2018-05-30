package org.openhr.application.hr.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.hr.repository.HrRepository;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.service.UserService;
import org.openhr.common.enumeration.Role;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.openhr.common.exception.UserAlreadyExists;
import org.openhr.common.proxy.worker.WorkerProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class HrServiceTest {
  private final HrTeamMember hrTeamMember = new HrTeamMember();
  private final User user = new User("Username", "Password");

  @Autowired private HrService hrService;

  @MockBean private UserService userService;

  @MockBean private HrRepository hrRepository;

  @MockBean private WorkerProxy workerProxy;

  @Before
  public void setUp() {
    hrTeamMember.setUser(user);
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = UserAlreadyExists.class)
  public void addHrTeamMemberShouldThrowErrorIfUsernameIsTaken() throws UserAlreadyExists {
    when(userService.isUsernameFree("Username")).thenReturn(false);

    hrService.addHrTeamMember(hrTeamMember);
  }

  @Test
  public void addHrTeamMemberShouldCallRepositoryWithUpdatedObject() throws UserAlreadyExists {
    when(userService.isUsernameFree("Username")).thenReturn(true);
    when(hrRepository.addHrTeamMember(anyObject())).thenAnswer(answer -> answer.getArguments()[0]);

    final HrTeamMember actualHrTeamMember = hrService.addHrTeamMember(hrTeamMember);

    assertNotEquals("Password", actualHrTeamMember.getUser().getPassword());
    assertEquals(Role.HRTEAMMEMBER, actualHrTeamMember.getRole());
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void addManagerToHrShouldThrowErrorIfHrTeamMemberIsNull()
      throws SubjectDoesNotExistException {
    when(hrRepository.getHrTeamMember(1L)).thenReturn(null);

    hrService.addManagerToHr(1L, 2L);
  }

  @Test(expected = SubjectDoesNotExistException.class)
  public void addManagerToHrShouldThrowErrorIfManagerIsNull() throws SubjectDoesNotExistException {
    when(hrRepository.getHrTeamMember(1L)).thenReturn(hrTeamMember);
    when(workerProxy.getManager(2L)).thenReturn(null);

    hrService.addManagerToHr(1L, 2L);
  }
}
