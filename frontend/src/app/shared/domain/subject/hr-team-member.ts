import { Subject } from '@shared/domain/subject/subject';
import { Manager } from '@shared/domain/subject/manager';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { ContactInformation } from '@shared/domain/subject/contact-information';
import { EmployeeInformation } from '@shared/domain/subject/employee-information';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { Role } from '@shared/domain/subject/role';

export class HrTeamMember extends Subject {
  public managers: Array<Manager>;

  constructor(
    personalInformation: PersonalInformation,
    contactInformation: ContactInformation,
    employeeInformation: EmployeeInformation,
    hrInformation: HrInformation,
    role: Role,
    managers: Array<Manager>
  ) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, role);
    this.managers = managers;
  }
}
