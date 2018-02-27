package org.openhr.application.hr.service;

import org.openhr.application.hr.domain.HrTeamMember;

public interface HrService {
  HrTeamMember getHrTeamMember(long subjectId);

  HrTeamMember addHrTeamMember(HrTeamMember hrTeamMember);

  HrTeamMember updateHrTeamMember(long subjectId, HrTeamMember hrTeamMember);
}
