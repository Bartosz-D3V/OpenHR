import { MomentInput } from 'moment';

export class DelegationApplication {
  public readonly subjectId: number;
  public readonly firstName: string;
  public readonly middleName?: string;
  public readonly lastName: string;
  public readonly position: string;
  public readonly department: string;
  public startDate: MomentInput;
  public endDate: MomentInput;
  public readonly canExceedExpenses = false;
  public readonly approvedByManager = false;
  public readonly approvedByHR = false;

  constructor() {
  }
}
