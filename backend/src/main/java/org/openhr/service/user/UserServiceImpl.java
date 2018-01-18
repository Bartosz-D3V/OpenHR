package org.openhr.service.user;

import org.openhr.domain.user.User;
import org.openhr.repository.user.UserRepository;
import org.openhr.service.authentication.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final AuthenticationService authenticationService;

  public UserServiceImpl(final UserRepository userRepository,
                         final AuthenticationService authenticationService) {
    this.userRepository = userRepository;
    this.authenticationService = authenticationService;
  }

  @Override
  public User findByUsername(final String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public void registerUser(final User user) {
    userRepository.registerUser(user);
  }

  @Override
  public String getEncodedPassword(final String username) {
    return userRepository.getEncodedPassword(username);
  }

  @Override
  public boolean validCredentials(final String username, final String password) {
    final String encodedPassword = findByUsername(username).getPassword();
    return authenticationService.passwordsMatch(password, encodedPassword);
  }
}
