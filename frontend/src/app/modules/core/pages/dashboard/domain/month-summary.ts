import { Month } from '@shared/constants/enumeration/month';

export class MonthSummary {
  public month: Month;
  public numberOfApplications: number;

  constructor(month: Month, numberOfApplications: number) {
    this.month = month;
    this.numberOfApplications = numberOfApplications;
  }
}
