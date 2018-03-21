package org.openhr.application.delegationapplication.dao;

import org.hibernate.SessionFactory;
import org.openhr.application.delegationapplication.domain.DelegationApplication;
import org.openhr.common.dao.BaseDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DelegationApplicationDAOImpl extends BaseDAO implements DelegationApplicationDAO {
  public DelegationApplicationDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(propagation = Propagation.MANDATORY)
  public DelegationApplication createDelegationApplication(final DelegationApplication delegationApplication) {
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
  public DelegationApplication updateDelegationApplication(final DelegationApplication delegationApplication) {
    super.merge(delegationApplication);
    return delegationApplication;
  }
}
