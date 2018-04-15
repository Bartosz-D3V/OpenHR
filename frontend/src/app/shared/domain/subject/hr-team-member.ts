import { Subject } from '@shared/domain/subject/subject';
import { PersonalInformation } from '@shared/domain/subject/personal-information';
import { ContactInformation } from '@shared/domain/subject/contact-information';
import { EmployeeInformation } from '@shared/domain/subject/employee-information';
import { HrInformation } from '@shared/domain/subject/hr-information';
import { Role } from '@shared/domain/subject/role';

export class HrTeamMember extends Subject {
  constructor(
    personalInformation: PersonalInformation,
    contactInformation: ContactInformation,
    employeeInformation: EmployeeInformation,
    hrInformation: HrInformation,
    role: Role
  ) {
    super(personalInformation, contactInformation, employeeInformation, hrInformation, role);
  }
}
