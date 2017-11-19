export class PersonalInformation {
  public middleName: string;
  public dateOfBirth: Date;
  public position: string;

  constructor(dateOfBirth: Date, position: string, middleName?: string) {
    this.middleName = middleName ? middleName : null;
    this.dateOfBirth = dateOfBirth;
    this.position = position;
  }
}
