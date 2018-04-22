package org.openhr.application.user.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.enumeration.Role;
import org.openhr.common.util.bean.BeanUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAOImpl extends BaseDAO implements UserDAO {

  public UserDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User getUser(final long userId) {
    return (User) super.get(User.class, userId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public void registerUser(final User user) {
    final List<UserRole> permissions = new ArrayList<>();
    final UserRole userRole = new UserRole(Role.EMPLOYEE);
    permissions.add(userRole);
    user.setUserRoles(permissions);
    userRole.setUser(user);
    super.save(user);
    super.save(userRole);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public User updateUser(final long userId, final User user) {
    final User savedUser = (User) super.get(User.class, userId);
    BeanUtil.copyNotNullProperties(user, savedUser, "userId");
    BeanUtil.copyNotNullProperties(user.getUserRoles(), savedUser.getUserRoles(), "user");
    super.merge(savedUser);
    return savedUser;
  }
}
