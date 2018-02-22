export class PersonalInformation {
  public firstName: string;
  public lastName: string;
  public middleName: string;
  public dateOfBirth: Date;

  constructor(firstName: string, lastName: string, middleName: string, dateOfBirth: Date) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.dateOfBirth = dateOfBirth;
  }
}
