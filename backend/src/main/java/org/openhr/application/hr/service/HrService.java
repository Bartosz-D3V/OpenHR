package org.openhr.application.hr.service;

import org.openhr.application.hr.domain.HrTeamMember;
import org.openhr.common.exception.SubjectDoesNotExistException;

public interface HrService {
  HrTeamMember getHrTeamMember(long subjectId);

  HrTeamMember addHrTeamMember(HrTeamMember hrTeamMember);

  HrTeamMember updateHrTeamMember(long subjectId, HrTeamMember hrTeamMember);

  void deleteHrTeamMember(long subjectId) throws SubjectDoesNotExistException;

  void addManagerToHr(long hrTeamMemberId, long managerId) throws SubjectDoesNotExistException;
}
