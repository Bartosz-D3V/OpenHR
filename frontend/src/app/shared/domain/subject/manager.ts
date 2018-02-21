import { Subject } from './subject';
import { Employee } from './employee';
import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';
import { HrInformation } from './hr-information';

export class Manager extends Subject {
  public employees?: Set<Employee>;

  constructor(firstName: string, lastName: string, personalInformation: PersonalInformation,
              contactInformation: ContactInformation, employeeInformation: EmployeeInformation,
              hrInformation: HrInformation, employees?: Set<Employee>) {
    super(firstName, lastName, personalInformation, contactInformation, employeeInformation, hrInformation);
    this.employees = employees;
  }
}
