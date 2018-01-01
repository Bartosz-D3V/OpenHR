export class EmployeeInformation {
  public nationalInsuranceNumber: string;
  public position: string;
  public employeeId?: string;
  public startDate: Date;
  public endDate: Date;

  constructor(nationalInsuranceNumber: string, position: string, startDate: Date, endDate: Date, employeeId?: string) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    this.position = position;
    this.startDate = startDate;
    this.endDate = endDate;
    this.employeeId = employeeId;
  }
}
