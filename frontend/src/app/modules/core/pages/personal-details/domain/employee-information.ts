export class EmployeeInformation {
  public nationalInsuranceNumber: string;
  public employeeId: string;
  public startDate: Date;
  public endDate: Date;

  constructor(nationalInsuranceNumber: string, employeeId: string, startDate: Date, endDate: Date) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    this.employeeId = employeeId;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
