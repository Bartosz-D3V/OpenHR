import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';

export class Subject {
  public subjectId: number;
  public firstName: string;
  public lastName: string;
  public personalInformation: PersonalInformation;
  public contactInformation: ContactInformation;
  public employeeInformation: EmployeeInformation;

  constructor(firstName: string, lastName: string, personalInformation: PersonalInformation,
              contactInformation: ContactInformation, employeeInformation: EmployeeInformation) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
  }
}
