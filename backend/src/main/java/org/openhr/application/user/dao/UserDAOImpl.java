package org.openhr.application.user.dao;

import org.hibernate.SessionFactory;
import org.openhr.application.user.domain.User;
import org.openhr.application.user.domain.UserRole;
import org.openhr.common.dao.BaseDAO;
import org.openhr.common.enumeration.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl extends BaseDAO implements UserDAO {

  public UserDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void registerUser(final User user) {
    final List<UserRole> permissions = new ArrayList<>();
    final UserRole userRole = new UserRole(Role.EMPLOYEE);
    permissions.add(userRole);
    user.setUserRoles(permissions);
    userRole.setUser(user);
    super.save(user);
    super.save(userRole);
  }
}