export class User {
  public subjectId: number;
  public firstName: string;
  public lastName: string;

  constructor(subjectId: number, firstName: string, lastName: string) {
    this.subjectId = subjectId;
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
