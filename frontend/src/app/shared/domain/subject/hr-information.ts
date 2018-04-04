export class HrInformation {
  public hrInformationId: number;
  public allowance: number;
  public usedAllowance: number;

  constructor(allowance: number, usedAllowance: number) {
    this.allowance = allowance;
    this.usedAllowance = usedAllowance;
  }
}
