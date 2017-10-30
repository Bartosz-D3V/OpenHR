package org.openhr.controller.personaldetails;

import org.hibernate.HibernateException;
import org.openhr.domain.subject.Subject;
import org.openhr.facade.personaldetails.PersonalDetailsFacade;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional(readOnly = true)
  @ResponseBody
  @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public Subject getSubjectDetails(@RequestParam final long subjectId) throws SubjectDoesNotExistException {
    return this.personalDetailsFacade.getSubjectDetails(subjectId);
  }

  @Transactional
  @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
          produces = {MediaType.APPLICATION_JSON_VALUE})
  public void createSubject(@RequestBody final Subject subject) throws HibernateException {
    this.personalDetailsFacade.addSubject(subject);
  }
}
