import { BankHoliday } from './bank-holiday';

export class BankHolidayEngland {
  public division: string;
  public events: Array<BankHoliday>;

  constructor(division: string, events: Array<BankHoliday>) {
    this.division = division;
    this.events = events;
  }
}
