import { MomentInput } from 'moment';

import { Application } from '@shared/domain/application/application';
import { Country } from '@shared/domain/country/country';
import { ApplicationType } from '@shared/constants/enumeration/application-type';

export class DelegationApplication extends Application {
  public country: Country;
  public city: string;
  public objective: string;
  public budget: number;

  constructor(
    applicationId: number,
    startDate: MomentInput,
    endDate: MomentInput,
    approvedByManager: boolean,
    approvedByHR: boolean,
    terminated: boolean,
    processInstanceId: string,
    country: Country,
    city: string,
    objective: string,
    budget: number
  ) {
    super(applicationId, startDate, endDate, approvedByManager, approvedByHR, terminated, processInstanceId);
    this.applicationType = ApplicationType.DELEGATION_APPLICATION;
    this.country = country;
    this.city = city;
    this.objective = objective;
    this.budget = budget;
  }
}
