export class LightweightSubject {
  public subjectId: number;
  public firstName: string;
  public lastName: string;
  public position: string;
  public fullName: string;

  constructor(subjectId: number, firstName: string, lastName: string, position: string) {
    this.subjectId = subjectId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.position = position;
    this.fullName = firstName + ' ' + lastName;
  }
}
