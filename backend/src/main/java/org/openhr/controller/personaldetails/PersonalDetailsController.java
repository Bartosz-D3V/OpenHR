package org.openhr.controller.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.domain.subject.ContactInformation;
import org.openhr.domain.subject.EmployeeInformation;
import org.openhr.domain.subject.PersonalInformation;
import org.openhr.domain.subject.Subject;
import org.openhr.exception.SubjectDoesNotExistException;
import org.openhr.facade.personaldetails.PersonalDetailsFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/personal-details")
public class PersonalDetailsController {

  private final PersonalDetailsFacade personalDetailsFacade;

  public PersonalDetailsController(final PersonalDetailsFacade personalDetailsFacade) {
    this.personalDetailsFacade = personalDetailsFacade;
  }

  @ResponseBody
  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public Subject getSubjectDetails(@RequestParam final long subjectId) throws SubjectDoesNotExistException {
    return personalDetailsFacade.getSubjectDetails(subjectId);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  public void createSubject(@RequestBody final Subject subject) throws HibernateException {
    personalDetailsFacade.addSubject(subject);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
  public void updateSubject(@RequestParam final long subjectId, @RequestBody final Subject subject)
    throws HibernateException, SubjectDoesNotExistException {
    personalDetailsFacade.updateSubject(subjectId, subject);
  }

  @RequestMapping(value = "personal-information", method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void updateSubjectPersonalInformation(@RequestParam final long subjectId,
                                               @RequestBody final PersonalInformation personalInformation)
    throws HibernateException, SubjectDoesNotExistException {
    personalDetailsFacade.updateSubjectPersonalInformation(subjectId, personalInformation);
  }

  @RequestMapping(value = "contact-information", method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void updateSubjectContactInformation(@RequestParam final long subjectId,
                                              @RequestBody final ContactInformation contactInformation)
    throws HibernateException, SubjectDoesNotExistException {
    personalDetailsFacade.updateSubjectContactInformation(subjectId, contactInformation);
  }

  @RequestMapping(value = "employee-information", method = RequestMethod.PUT,
    consumes = {MediaType.APPLICATION_JSON_VALUE})
  public void updateSubjectEmployeeInformation(@RequestParam final long subjectId,
                                               @RequestBody final EmployeeInformation employeeInformation)
    throws HibernateException, SubjectDoesNotExistException {
    personalDetailsFacade.updateSubjectEmployeeInformation(subjectId, employeeInformation);
  }

  @RequestMapping(method = RequestMethod.DELETE)
  public void deleteSubject(@RequestParam final long subjectId) throws HibernateException,
    SubjectDoesNotExistException {
    personalDetailsFacade.deleteSubject(subjectId);
  }
}
