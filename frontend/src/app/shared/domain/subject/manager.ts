import { Subject } from './subject';
import { Employee } from './employee';
import { PersonalInformation } from './personal-information';
import { ContactInformation } from './contact-information';
import { EmployeeInformation } from './employee-information';
import { HrInformation } from './hr-information';
import { Role } from './role';
import { HrTeamMember } from '@shared/domain/subject/hr-team-member';

export class Manager extends Subject {
  public employees?: Set<Employee>;
  public hrTeamMember?: HrTeamMember;

  constructor(
    personalInformation: PersonalInformation,
    contactInformation: ContactInformation,
    employeeInformation: EmployeeInformation,
    hrInformation: HrInformation,
    role: Role,
    employees?: Set<Employee>,
    hrTeamMember?: HrTeamMember
  ) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, role);
    this.employees = employees;
    this.hrTeamMember = hrTeamMember;
  }
}
