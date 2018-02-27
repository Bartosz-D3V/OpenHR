package org.openhr.application.hr.dao;

import org.openhr.application.hr.domain.HrTeamMember;

public interface HrDAO {
  HrTeamMember getHrTeamMember(long subjectId);

  HrTeamMember addHrTeamMember(HrTeamMember hrTeamMember);

  HrTeamMember updateHrTeamMember(long subjectId, HrTeamMember hrTeamMember);
}
