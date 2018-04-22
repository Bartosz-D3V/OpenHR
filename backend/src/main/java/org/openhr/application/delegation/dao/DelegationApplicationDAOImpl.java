package org.openhr.application.delegation.dao;

import org.hibernate.SessionFactory;
import org.openhr.application.delegation.domain.DelegationApplication;
import org.openhr.common.dao.BaseDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DelegationApplicationDAOImpl extends BaseDAO implements DelegationApplicationDAO {
  public DelegationApplicationDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication createDelegationApplication(
      final DelegationApplication delegationApplication) {
    super.save(delegationApplication);
    return delegationApplication;
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public DelegationApplication getDelegationApplication(final long delegationApplicationId) {
    return (DelegationApplication) super.get(DelegationApplication.class, delegationApplicationId);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication updateDelegationApplication(
      final DelegationApplication delegationApplication) {
    super.merge(delegationApplication);
    return delegationApplication;
  }
}
