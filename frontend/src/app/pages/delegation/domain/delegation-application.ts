import { Destination } from './destination';

export class DelegationApplication {
  public subjectId: number;
  public firstName: string;
  public middleName?: string;
  public lastName: string;
  public position: string;
  public department: string;
  public destination: Set<Destination>;
  public selectedDays: Array<Date>;
  public budget: number;
  public meansOfTransport: Array<string>;
  public canExceedExpenses: boolean;
  public approvedByManager = false;
  public approvedByHR = false;

  constructor() {
  }
}
