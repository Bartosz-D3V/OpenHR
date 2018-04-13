package org.openhr.application.subject.controller;

import java.util.List;
import org.hibernate.HibernateException;
import org.openhr.application.subject.dto.LightweightSubjectDTO;
import org.openhr.application.subject.facade.SubjectFacade;
import org.openhr.common.domain.subject.ContactInformation;
import org.openhr.common.domain.subject.EmployeeInformation;
import org.openhr.common.domain.subject.PersonalInformation;
import org.openhr.common.domain.subject.Subject;
import org.openhr.common.exception.SubjectDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectController {

  private final SubjectFacade subjectFacade;

  public SubjectController(final SubjectFacade subjectFacade) {
    this.subjectFacade = subjectFacade;
  }

  @RequestMapping(
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<Subject> getSubjects() {
    return subjectFacade.getSubjects();
  }

  @RequestMapping(
    value = "/{subjectId}",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  public Subject getSubjectDetails(@PathVariable final long subjectId)
      throws SubjectDoesNotExistException {
    return subjectFacade.getSubjectDetails(subjectId);
  }

  @RequestMapping(
    value = "/lightweight/{subjectId}",
    method = RequestMethod.GET,
    produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseBody
  public LightweightSubjectDTO getLightweightSubject(@PathVariable final long subjectId)
      throws SubjectDoesNotExistException {
    return subjectFacade.getLightweightSubject(subjectId);
  }

  @RequestMapping(
    value = "personal-information",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  public void updateSubjectPersonalInformation(
      @RequestParam final long subjectId,
      @RequestBody final PersonalInformation personalInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectFacade.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @RequestMapping(
    value = "contact-information",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  public void updateSubjectContactInformation(
      @RequestParam final long subjectId, @RequestBody final ContactInformation contactInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectFacade.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @RequestMapping(
    value = "employee-information",
    method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE}
  )
  public void updateSubjectEmployeeInformation(
      @RequestParam final long subjectId,
      @RequestBody final EmployeeInformation employeeInformation)
      throws HibernateException, SubjectDoesNotExistException {
    subjectFacade.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @RequestMapping(method = RequestMethod.DELETE)
  public void deleteSubject(@RequestParam final long subjectId)
      throws HibernateException, SubjectDoesNotExistException {
    subjectFacade.deleteSubject(subjectId);
  }
}
