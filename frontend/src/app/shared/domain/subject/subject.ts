import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';
import { HrInformation } from './hr-information';
import { Role } from './role';

export abstract class Subject {
  public subjectId: number;
  public firstName: string;
  public lastName: string;
  public fullName?: string;
  public personalInformation: PersonalInformation;
  public contactInformation: ContactInformation;
  public employeeInformation: EmployeeInformation;
  public hrInformation: HrInformation;
  public role: Role;

  constructor(firstName: string, lastName: string, personalInformation: PersonalInformation,
              contactInformation: ContactInformation, employeeInformation: EmployeeInformation,
              hrInformation: HrInformation, role: Role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = firstName + ' ' + lastName;
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
    this.hrInformation = hrInformation;
    this.role = role;
  }
}
