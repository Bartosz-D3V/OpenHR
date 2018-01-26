package org.openhr.repository.user;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openhr.domain.user.User;
import org.openhr.domain.user.UserRole;
import org.openhr.enumeration.UserPermissionRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
  private final SessionFactory sessionFactory;
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public UserRepositoryImpl(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public User findByUsername(final String username) {
    User user;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      user = (User) criteria
        .add(Restrictions.eq("username", username))
        .setMaxResults(1)
        .uniqueResult();
      session.close();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return user;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public long findUserId(final String username) {
    long userId;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      userId = (long) criteria
        .setProjection(Projections.property("userId"))
        .add(Restrictions.eq("username", username))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return userId;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void registerUser(final User user) {
    final List<UserRole> permissions = new ArrayList<>();
    final UserRole userRole = new UserRole(UserPermissionRole.MEMBER);
    permissions.add(userRole);
    user.setUserRoles(permissions);
    userRole.setUser(user);
    try {
      final Session session = sessionFactory.openSession();
      session.save(userRole);
      session.save(user);
      session.close();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public String getEncodedPassword(final long userId) {
    String encodedPassword;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      encodedPassword = (String) criteria
        .setProjection(Projections.property("password"))
        .add(Restrictions.eq("userId", userId))
        .uniqueResult();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return encodedPassword;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public boolean usernameIsFree(final String username) {
    boolean usernameIsFree;
    try {
      final Session session = sessionFactory.openSession();
      final Criteria criteria = session.createCriteria(User.class);
      int numberOfUsernames = criteria
        .add(Restrictions.eq("username", username))
        .setReadOnly(true)
        .list()
        .size();
      session.close();
      usernameIsFree = numberOfUsernames < 1;
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }

    return usernameIsFree;
  }
}
