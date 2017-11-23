import { Role } from '../../constants/enumeration/role';

export class Subject {
  public subjectId: number;
  public firstName: string;
  public lastName: string;
  public role: Role;
  public fullName: string;

  constructor(subjectId: number, firstName: string, lastName: string, role: Role) {
    this.subjectId = subjectId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.fullName = firstName + ' ' + lastName;
  }
}
