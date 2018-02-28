package org.openhr.application.hr.dao;

import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.application.manager.domain.Manager;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface HrDAO {
  HrTeamMember getHrTeamMember(long subjectId);

  HrTeamMember addHrTeamMember(HrTeamMember hrTeamMember);

  HrTeamMember updateHrTeamMember(long subjectId, HrTeamMember hrTeamMember);

  void addManagerToHr(HrTeamMember hrTeamMember, Manager manager);
}
