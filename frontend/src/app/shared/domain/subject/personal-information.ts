import { MomentInput } from 'moment';

export class PersonalInformation {
  public firstName: string;
  public lastName: string;
  public middleName: string;
  public dateOfBirth: MomentInput;

  constructor(firstName: string, lastName: string, middleName: string, dateOfBirth: MomentInput) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.dateOfBirth = dateOfBirth;
  }
}
