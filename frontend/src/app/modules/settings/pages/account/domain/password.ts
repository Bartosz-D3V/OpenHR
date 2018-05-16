export class Password {
  public oldPassword: string;
  public newPassword: string;
  public newPasswordRepeat: string;

  constructor(oldPassword: string, newPassword: string, newPasswordRepeat: string) {
    this.oldPassword = oldPassword;
    this.newPassword = newPassword;
    this.newPasswordRepeat = newPasswordRepeat;
  }
}
