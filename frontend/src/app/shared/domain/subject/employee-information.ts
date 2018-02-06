import { MomentInput } from 'moment';

export class EmployeeInformation {
  public nationalInsuranceNumber: string;
  public position: string;
  public department: string;
  public startDate: MomentInput;
  public endDate: MomentInput;

  constructor(nationalInsuranceNumber: string, position: string, department: string, startDate: MomentInput, endDate: MomentInput) {
    this.nationalInsuranceNumber = nationalInsuranceNumber;
    this.position = position;
    this.department = department;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
