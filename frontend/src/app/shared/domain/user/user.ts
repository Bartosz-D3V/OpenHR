export class User {
  public subjectId: number;
  public userName: string;
  public fullName: string;

  constructor(subjectId: number, userName: string, fullName: string) {
    this.subjectId = subjectId;
    this.userName = userName;
    this.fullName = fullName;
  }
}
