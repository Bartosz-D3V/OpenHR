import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';
import { HrInformation } from './hr-information';
import { Role } from './role';
import { User } from '@modules/settings/pages/settings/domain/user';

export abstract class Subject {
  public subjectId: number;
  public personalInformation: PersonalInformation;
  public contactInformation: ContactInformation;
  public employeeInformation: EmployeeInformation;
  public hrInformation: HrInformation;
  public role: Role;
  public user?: User;

  protected constructor(
    personalInformation: PersonalInformation,
    contactInformation: ContactInformation,
    employeeInformation: EmployeeInformation,
    hrInformation: HrInformation,
    role: Role
  ) {
    this.personalInformation = personalInformation;
    this.contactInformation = contactInformation;
    this.employeeInformation = employeeInformation;
    this.hrInformation = hrInformation;
    this.role = role;
  }
}
