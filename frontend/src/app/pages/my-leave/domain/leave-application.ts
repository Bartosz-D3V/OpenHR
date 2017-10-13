export class LeaveApplication {
  public readonly subjectId: number;
  public leaveType: string;
  public selectedDays: Array<Date>;
  public message?: string;

  constructor() {
  }
}
