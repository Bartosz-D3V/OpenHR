import { MomentInput } from 'moment';

export class AllowanceSettings {
  public autoResetAllowance: boolean;
  public resetDate: MomentInput;
  public carryOverUnusedLeave: boolean;
  public numberOfDaysToCarryOver: number;

  constructor() {}
}
