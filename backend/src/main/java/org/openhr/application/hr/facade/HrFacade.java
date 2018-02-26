package org.openhr.application.hr.facade;

import org.openhr.application.hr.domain.HrTeamMember;

public interface HrFacade {
  HrTeamMember getHrTeamMember(long subjectId);

  HrTeamMember addHrTeamMember(HrTeamMember hrTeamMember);

  HrTeamMember updateHrTeamMember(long subjectId, HrTeamMember hrTeamMember);
}
