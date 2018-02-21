import { Subject } from './subject';
import { Manager } from './manager';
import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';
import { HrInformation } from './hr-information';

export class Employee extends Subject {
  public manager?: Manager;

  constructor(firstName: string, lastName: string, personalInformation: PersonalInformation,
              contactInformation: ContactInformation, employeeInformation: EmployeeInformation,
              hrInformation: HrInformation, manager?: Manager) {
    super(firstName, lastName, personalInformation, contactInformation, employeeInformation, hrInformation);
    this.manager = manager;
  }
}
