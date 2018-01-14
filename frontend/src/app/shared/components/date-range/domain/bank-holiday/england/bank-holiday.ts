import { MomentInput } from 'moment';

export interface BankHoliday {
  title: string;
  date: MomentInput;
  notes: string;
  bunting: boolean;
}
