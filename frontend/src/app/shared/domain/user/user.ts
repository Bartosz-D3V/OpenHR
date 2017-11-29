export class User {
  public subjectId: number;
  public email: string;
  public fullName: string;
  public token: string = null;

  constructor(subjectId: number, email: string, fullName: string, token: string) {
    this.subjectId = subjectId;
    this.email = email;
    this.fullName = fullName;
    this.token = token;
  }
}
