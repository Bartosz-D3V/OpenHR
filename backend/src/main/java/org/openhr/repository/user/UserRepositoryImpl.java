package org.openhr.repository.user;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openhr.domain.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional(propagation = Propagation.REQUIRED)
  public void registerUser(final User user) {
    try {
      final Session session = sessionFactory.openSession();
      session.save(user);
      session.close();
    } catch (final HibernateException e) {
      log.error(e.getLocalizedMessage());
      throw e;
    }
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public String getEncodedPassword(final String username) {
    return findByUsername(username).getPassword();
  }

}
