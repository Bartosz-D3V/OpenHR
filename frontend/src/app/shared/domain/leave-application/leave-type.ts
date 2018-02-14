export class LeaveType {
  public leaveTypeId: number;
  public leaveCategory: string;
  public description: string;

  constructor(leaveTypeId: number, leaveCategory: string, description: string) {
    this.leaveTypeId = leaveTypeId;
    this.leaveCategory = leaveCategory;
    this.description = description;
  }
}
