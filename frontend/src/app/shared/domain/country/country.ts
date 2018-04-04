export class Country {
  public countryId: number;
  public countryName: string;
  public flagUrl: string;

  constructor(countryName: string, flagUrl: string) {
    this.countryName = countryName;
    this.flagUrl = flagUrl;
  }
}
