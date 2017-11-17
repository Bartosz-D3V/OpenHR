export class Subject {
  public subjectId: number;
  public firstName: string;
  public lastName: string;
  public fullName: string;

  constructor(subjectId: number, firstName: string, lastName: string) {
    this.subjectId = subjectId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.fullName = firstName + ' ' + lastName;
  }
}
