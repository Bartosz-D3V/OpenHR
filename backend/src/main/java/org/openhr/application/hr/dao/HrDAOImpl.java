package org.openhr.application.hr.dao;

import org.hibernate.SessionFactory;
import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.common.dao.BaseDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class HrDAOImpl extends BaseDAO implements HrDAO {

  public HrDAOImpl(final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public HrTeamMember getHrTeamMember(final long subjectId) {
    return (HrTeamMember) super.get(HrTeamMember.class, subjectId);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember addHrTeamMember(final HrTeamMember hrTeamMember) {
    super.save(hrTeamMember);

    return hrTeamMember;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public HrTeamMember updateHrTeamMember(final long subjectId, final HrTeamMember hrTeamMember) {
    final HrTeamMember savedHrTeamMember = getHrTeamMember(subjectId);
    BeanUtils.copyProperties(hrTeamMember, savedHrTeamMember, "subjectId");
    super.merge(savedHrTeamMember);

    return savedHrTeamMember;
  }
}
