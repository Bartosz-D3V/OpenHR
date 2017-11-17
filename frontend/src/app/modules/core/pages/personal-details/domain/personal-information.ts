export class PersonalInformation {
  public firstName: string;
  public middleName: string;
  public lastName: string;
  public dateOfBirth: Date;
  public position: string;

  constructor(firstName: string, lastName: string, dateOfBirth: Date, position: string, middleName?: string) {
    this.firstName = firstName;
    this.middleName = middleName ? middleName : null;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.position = position;
  }
}
