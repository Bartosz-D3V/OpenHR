export class ErrorInfo {
  public url: string;
  public message: string;

  constructor(url: string, message: string) {
    this.url = url;
    this.message = message;
  }
}
