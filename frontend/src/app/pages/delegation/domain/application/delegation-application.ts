import { Delegation } from '../delegation/delegation';

export class DelegationApplication {
  public readonly subjectId: number;
  public readonly firstName: string;
  public readonly middleName?: string;
  public readonly lastName: string;
  public readonly position: string;
  public readonly department: string;
  public delegations: Array<Delegation>;
  public readonly canExceedExpenses = false;
  public readonly approvedByManager = false;
  public readonly approvedByHR = false;

  constructor() {
  }
}
