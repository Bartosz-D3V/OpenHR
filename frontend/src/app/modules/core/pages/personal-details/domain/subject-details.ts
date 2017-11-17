import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';

export class SubjectDetails {
  public subjectId: number;
  public personalInformation: PersonalInformation;
  public contactInformation: ContactInformation;
  public employeeInformation: EmployeeInformation;

  constructor(subjectId: number, personalInformation: PersonalInformation, contactInformation: ContactInformation,
              employeeInformation: EmployeeInformation) {
    this.subjectId = subjectId;
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
  }
}
