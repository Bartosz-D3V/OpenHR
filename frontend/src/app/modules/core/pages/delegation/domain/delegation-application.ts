import { MomentInput } from 'moment';

import { Subject } from '@shared/domain/subject/subject';

export class DelegationApplication {
  public subject: Subject;
  public country: string;
  public city: string;
  public objective: string;
  public budget: number;
  public startDate: MomentInput;
  public endDate: MomentInput;

  constructor() {}
}
