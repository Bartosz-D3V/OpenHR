import { Subject } from './subject';
import { Employee } from './employee';
import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';
import { HrInformation } from './hr-information';
import { Role } from './role';

export class Manager extends Subject {
  public employees?: Set<Employee>;

  constructor(firstName: string, lastName: string, personalInformation: PersonalInformation,
              contactInformation: ContactInformation, employeeInformation: EmployeeInformation,
              hrInformation: HrInformation, role: Role, employees?: Set<Employee>) {
    super(firstName, lastName, personalInformation, contactInformation, employeeInformation, hrInformation, role);
    this.employees = employees;
  }
}
