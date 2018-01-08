package org.openhr.service.user;

import org.openhr.domain.user.User;
import org.openhr.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User findByEmail(final String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public void registerUser(final User user) {
    userRepository.registerUser(user);
  }
}
