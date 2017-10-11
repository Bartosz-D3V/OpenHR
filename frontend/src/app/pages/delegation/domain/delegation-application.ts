import { Destination } from './destination';

export class DelegationApplication {
  public readonly subjectId: number;
  public readonly firstName: string;
  public readonly middleName?: string;
  public readonly lastName: string;
  public readonly position: string;
  public readonly department: string;
  public destination: Array<Destination>;
  public selectedDays: Array<Date>;
  public budget: number;
  public meansOfTransport: Array<string>;
  public readonly canExceedExpenses = false;
  public readonly approvedByManager = false;
  public readonly approvedByHR = false;

  constructor() {
  }
}
