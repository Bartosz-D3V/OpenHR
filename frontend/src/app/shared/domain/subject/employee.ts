import { Subject } from './subject';
import { Manager } from './manager';
import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';
import { HrInformation } from './hr-information';
import { Role } from './role';

export class Employee extends Subject {
  public manager?: Manager;

  constructor(
    personalInformation: PersonalInformation,
    contactInformation: ContactInformation,
    employeeInformation: EmployeeInformation,
    hrInformation: HrInformation,
    role: Role,
    manager?: Manager
  ) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, role);
    this.manager = manager;
  }
}
