export class LeaveApplication {
  private _subjectId: number;
  private _leaveType: string;
  private _selectedDays: Array<Date>;
  private _message?: string;

  constructor() {
  }

  get subjectId(): number {
    return this._subjectId;
  }

  set subjectId(value: number) {
    this._subjectId = value;
  }

  get leaveType(): string {
    return this._leaveType;
  }

  set leaveType(value: string) {
    this._leaveType = value;
  }

  get selectedDays(): Array<Date> {
    return this._selectedDays;
  }

  set selectedDays(value: Array<Date>) {
    this._selectedDays = value;
  }

  get message(): string {
    return this._message;
  }

  set message(value: string) {
    this._message = value;
  }
}
