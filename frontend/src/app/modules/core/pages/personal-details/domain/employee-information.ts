export class EmployeeInformation {
  public nationalInsuranceNumber: string;
  public position: string;
  public employeeId: string;
  public startDate: Date;
  public endDate: Date;

  constructor(nationalInsuranceNumber: string, position: string, employeeId: string, startDate: Date, endDate: Date) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    this.position = position;
    this.employeeId = employeeId;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
