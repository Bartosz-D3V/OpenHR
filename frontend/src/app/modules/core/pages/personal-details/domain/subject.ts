import { Role } from '../../../../../shared/constants/enumeration/role';
import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';

export class Subject {
  public subjectId: number;
  public firstName: string;
  public lastName: string;
  public role: Role;
  public personalInformation: PersonalInformation;
  public contactInformation: ContactInformation;
  public employeeInformation: EmployeeInformation;

  constructor(firstName: string, lastName: string, role: Role, personalInformation: PersonalInformation,
              contactInformation: ContactInformation, employeeInformation: EmployeeInformation) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
  }
}
